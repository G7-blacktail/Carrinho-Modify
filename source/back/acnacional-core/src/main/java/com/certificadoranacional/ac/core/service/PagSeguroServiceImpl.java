package com.certificadoranacional.ac.core.service;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.Log;
import com.certificadoranacional.ac.core.config.AcConfigurationProperties;
import com.certificadoranacional.ac.core.event.PedidoEvent;
import com.certificadoranacional.ac.core.event.PedidoEventType;
import com.certificadoranacional.ac.core.utils.CharUtils;
import com.certificadoranacional.ac.core.utils.DateUtils;
import com.certificadoranacional.ac.jpa.entity.Cliente;
import com.certificadoranacional.ac.jpa.entity.Pedido;
import com.certificadoranacional.ac.jpa.entity.PedidoProduto;
import com.certificadoranacional.ac.jpa.entity.Posto;
import com.certificadoranacional.ac.jpa.entity.Produto;
import com.certificadoranacional.ac.jpa.entity.Voucher;
import com.certificadoranacional.ac.jpa.repository.PedidoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.VoucherJpaRepository;

import br.com.uol.pagseguro.api.Endpoints;
import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.checkout.CheckoutRegistrationBuilder;
import br.com.uol.pagseguro.api.common.domain.DataList;
import br.com.uol.pagseguro.api.common.domain.ShippingType.Type;
import br.com.uol.pagseguro.api.common.domain.TransactionStatus;
import br.com.uol.pagseguro.api.common.domain.builder.AcceptedPaymentMethodsBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.AddressBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentItemBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentMethodBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.SenderBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ShippingBuilder;
import br.com.uol.pagseguro.api.common.domain.enums.Currency;
import br.com.uol.pagseguro.api.common.domain.enums.PaymentMethodGroup;
import br.com.uol.pagseguro.api.common.domain.enums.State;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.exception.PagSeguroException;
import br.com.uol.pagseguro.api.exception.PagSeguroServerException;
import br.com.uol.pagseguro.api.http.HttpClient;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import br.com.uol.pagseguro.api.transaction.search.TransactionSummary;
import br.com.uol.pagseguro.api.utils.logging.LoggerFactory;
import br.com.uol.pagseguro.api.utils.logging.Slf4jLoggerFactory;

@Service
public class PagSeguroServiceImpl implements PagSeguroService, InitializingBean {

  @Autowired
  private PedidoJpaRepository pedidoJpaRepository;

  @Autowired
  private VoucherJpaRepository voucherJpaRepository;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private AcConfigurationProperties arConfigurationProperties;

  private PagSeguro pagSeguro;

  private boolean sandbox;

  public PagSeguroServiceImpl() {
    super();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    LoggerFactory loggerFactory = new Slf4jLoggerFactory();
    HttpClient httpClient = new JSEHttpClient();
    Credential credential = Credential.sellerCredential(this.arConfigurationProperties.getPagseguro().getEmail(), this.arConfigurationProperties.getPagseguro().getToken());
    PagSeguroEnv pagSeguroEnv = PagSeguroEnv.valueOf(this.arConfigurationProperties.getPagseguro().getEnv());
    this.sandbox = pagSeguroEnv == PagSeguroEnv.SANDBOX;
    this.pagSeguro = PagSeguro.instance(loggerFactory, httpClient, credential, pagSeguroEnv);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public String send(final Pedido item) {
    try {
      Cliente cliente = item.getCliente();
      Posto posto = item.getPosto();
      String email = cliente.getEmail();
      String nome = Normalizer.normalize(cliente.getNome(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
      if (this.sandbox) {
        // Para testes o email deve ter o dominio sandbox.pagseguro.com.br
        email = "arlidersis@sandbox.pagseguro.com.br";
      }
      if (nome.split(" ").length < 2) {
        nome += " SSN";
      }

      nome = CharUtils.javaLetterOrDigitAndWhitespace(nome);

      // @formatter:off
      CheckoutRegistrationBuilder builder = new CheckoutRegistrationBuilder();
      builder.withCurrency(Currency.BRL);
      builder.withReference(item.getCodigo());
      builder.withSender(new SenderBuilder()
          .withEmail(email)
          .withName(nome)
          // .withCPF(cliente.getDocumento())
      );
      
      // TODO
      builder.withShipping(new ShippingBuilder()
          .withAddress(new AddressBuilder()
              .withCity(posto.getMunicipio().getNome())
              .withComplement(posto.getComplemento())
              .withCountry("Brasil")
              .withNumber(posto.getNumero())
              .withPostalCode(posto.getCep())
              .withState(State.valueOf(posto.getUf().getCodigo()))
              .withStreet(posto.getEndereco()).build())
          .withCost(BigDecimal.ZERO)
          .withType(Type.USER_CHOISE)
      );
      
      for(PedidoProduto subItem : item.getProdutoList()) {
        builder.addItem(new PaymentItemBuilder()
            .withId(subItem.getProduto().getCodigo())
            .withQuantity(subItem.getQuantidade().intValue())
            .withAmount(subItem.getValor())
            .withDescription(subItem.getProduto().getNome())
            .withWeight(1)
        );
      }
      builder.withAcceptedPaymentMethods(new AcceptedPaymentMethodsBuilder()
          .addInclude(new PaymentMethodBuilder().withGroup(PaymentMethodGroup.BANK_SLIP))
          .addInclude(new PaymentMethodBuilder().withGroup(PaymentMethodGroup.BALANCE))
          .addInclude(new PaymentMethodBuilder().withGroup(PaymentMethodGroup.CREDIT_CARD))
          .addInclude(new PaymentMethodBuilder().withGroup(PaymentMethodGroup.DEPOSIT))
          .addInclude(new PaymentMethodBuilder().withGroup(PaymentMethodGroup.ONLINE_DEBIT))
      );
      //builder.addPaymentMethodConfig(new PaymentMethodConfigBuilder()
      //    .withPaymentMethod(new PaymentMethodBuilder().withGroup(PaymentMethodGroup.CREDIT_CARD))
          //.withConfig(new ConfigBuilder().withKey(ConfigKey.MAX_INSTALLMENTS_NO_INTEREST).withValue(new BigDecimal(3)))
          //.withConfig(new ConfigBuilder()
          //    .withKey(ConfigKey.MAX_INSTALLMENTS_LIMIT)
          //    .withValue(new BigDecimal(3)))
      //);
      // @formatter:on

      String code = this.pagSeguro.checkouts().register(builder).getCheckoutCode();
      return code;
    } catch (PagSeguroException e) {
      if (e instanceof PagSeguroServerException) {
        String msg = ((PagSeguroServerException) e).getServerResponse().asString();
        throw new IllegalStateException(msg);
      }
      if (e.getMessage() != null) {
        throw new IllegalStateException(e);
      }
      throw new IllegalStateException("Erro ao enviar a solicitação para o PagSeguro");
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Boolean checkReference(final String id) {
    try {
      DataList<? extends TransactionSummary> dataList = this.pagSeguro.transactions().search().byReference(id);
      if (dataList != null) {
        for (TransactionSummary ts : dataList.getData()) {
          TransactionDetail transaction = ts.getDetail();
          Boolean b = this.doCheckTransaction(transaction);
          if (b.booleanValue()) {
            return Boolean.TRUE;
          }
        }
      }
    } catch (PagSeguroServerException e) {
      if (e.getServerResponse().getStatus() == HttpStatus.NOT_FOUND.value()) {
        Log.getLog().warn("Pedido não cadastrado no PagSeguro, cancelando " + id);
        Pedido pedido = this.pedidoJpaRepository.findByCodigo(id);
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(7);
        LocalDateTime dataPedido = DateUtils.toLocalDateTime(pedido.getData());
        if ((pedido != null) && (dataLimite.isAfter(dataPedido))) {
          pedido.setSituacao(Pedido.SITUACAO_CANCELADO);
          this.pedidoJpaRepository.save(pedido);
        }
      }
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Boolean checkTransaction(final String id) {
    TransactionDetail transaction = this.pagSeguro.transactions().search().byCode(id);
    return this.doCheckTransaction(transaction);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Boolean checkNotification(final String id) {
    TransactionDetail transaction = this.pagSeguro.transactions().search().byNotificationCode(id);
    return this.doCheckTransaction(transaction);
  }

  private Boolean doCheckTransaction(final TransactionDetail transaction) {
    if (transaction != null) {
      int status = transaction.getStatus().getStatusId();
      String reference = transaction.getReference();
      Pedido pedido = this.pedidoJpaRepository.findByCodigo(reference);
      if ((status == TransactionStatus.Status.APPROVED.ordinal()) || (status == TransactionStatus.Status.AVAILABLE.ordinal())) {
        if ((pedido != null) && (!pedido.getSituacao().equals(Pedido.SITUACAO_PAGO))) {
          Cliente cliente = pedido.getCliente();
          List<PedidoProduto> pedidoProdutoList = pedido.getProdutoList();
          for (int i = 0; i < pedidoProdutoList.size(); i++) {
            PedidoProduto pedidoProduto = pedidoProdutoList.get(i);
            Produto produto = pedidoProduto.getProduto();
            int quantidade = pedidoProduto.getQuantidade().intValue();
            for (int j = 0; j < quantidade; j++) {
              String codigo = pedido.getCodigo() + String.format("%03d", Integer.valueOf(i)) + String.format("%03d", Integer.valueOf(j));
              Voucher voucher = new Voucher();
              voucher.setAtivo(Boolean.TRUE);
              voucher.setCliente(cliente);
              voucher.setCodigo(codigo);
              voucher.setPedido(pedido);
              voucher.setPedidoProduto(pedidoProduto);
              voucher.setProduto(produto);
              this.voucherJpaRepository.save(voucher);
            }
          }
          pedido.setCodigoTransacaoPagamento(transaction.getCode());
          pedido.setSituacao(Pedido.SITUACAO_PAGO);
          this.pedidoJpaRepository.save(pedido);
          this.applicationEventPublisher.publishEvent(new PedidoEvent(pedido.getId(), PedidoEventType.PAGO));
          return Boolean.TRUE;
        }
      } else if (status == TransactionStatus.Status.CANCELLED.ordinal()) {
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(30);
        LocalDateTime dataPedido = DateUtils.toLocalDateTime(pedido.getData());
        if ((pedido != null) && (dataLimite.isAfter(dataPedido))) {
          pedido.setSituacao(Pedido.SITUACAO_CANCELADO);
          this.pedidoJpaRepository.save(pedido);
          return Boolean.FALSE;
        }
      }
    }
    return Boolean.FALSE;
  }

  @Override
  public String getUrl(final String code) {
    String url = String.format(Endpoints.CHECKOUT_REDIRECT_URL, this.pagSeguro.getHostRedirect(), code);
    if (url.startsWith("https://ws.")) {
      url = "https://" + url.substring(11);
    }
    return url;
  }

}
