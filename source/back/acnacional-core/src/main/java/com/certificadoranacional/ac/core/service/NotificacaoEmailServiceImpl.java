package com.certificadoranacional.ac.core.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.Log;
import com.certificadoranacional.ac.core.converter.PedidoJpaConverter;
import com.certificadoranacional.ac.core.converter.PedidoProdutoJpaConverter;
import com.certificadoranacional.ac.core.converter.VoucherJpaConverter;
import com.certificadoranacional.ac.core.model.PedidoRepresentation;
import com.certificadoranacional.ac.core.model.VoucherRepresentation;
import com.certificadoranacional.ac.jpa.entity.Pedido;
import com.certificadoranacional.ac.jpa.entity.Voucher;
import com.certificadoranacional.ac.jpa.repository.PedidoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.VoucherJpaRepository;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

@Service
public class NotificacaoEmailServiceImpl implements NotificacaoEmailService {

  private static final String FROM = "vendas@acnacional.com";

  private static final String BCC = "administrativo@acnacional.com";

  @Autowired
  private PedidoJpaRepository pedidoJpaRepository;

  @Autowired
  private PedidoJpaConverter pedidoJpaConverter;

  @Autowired
  private PedidoProdutoJpaConverter pedidoProdutoJpaConverter;

  @Autowired
  private VoucherJpaRepository voucherJpaRepository;

  @Autowired
  private VoucherJpaConverter voucherJpaConverter;

  @Autowired
  private MailService mailService;

  @Autowired
  private TemplateService templateService;

  public NotificacaoEmailServiceImpl() {
    super();
  }

  @Async
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void enviarEmailAguardandoPagamento(final String id) {
    try {
      TimeUnit.SECONDS.sleep(15);
      Pedido entity = this.pedidoJpaRepository.findById(id).orElse(null);
      PedidoRepresentation pedido = this.pedidoJpaConverter.convert(entity);
      pedido.setProdutoList(this.pedidoProdutoJpaConverter.convert(entity.getProdutoList()));
      pedido.setVoucherList(this.voucherJpaConverter.convert(entity.getVoucherList()));
      Map<String, Object> vars = Collections.singletonMap("pedido", pedido);
      String html = this.templateService.parse("email-aguardando-pagamento", vars);
      this.doSend(html, "Pedido finalizado", pedido.getCliente().getEmail());
    } catch (Exception e) {
      Log.getLog().error(e.getMessage(), e);
    }
  }

  @Async
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void enviarEmailConfirmacaoPagamento(final String id) {
    try {
      TimeUnit.SECONDS.sleep(15);
      Pedido entity = this.pedidoJpaRepository.findById(id).orElse(null);
      PedidoRepresentation pedido = this.pedidoJpaConverter.convert(entity);
      pedido.setProdutoList(this.pedidoProdutoJpaConverter.convert(entity.getProdutoList()));
      pedido.setVoucherList(this.voucherJpaConverter.convert(entity.getVoucherList()));
      Map<String, Object> vars = Collections.singletonMap("pedido", pedido);
      String html = this.templateService.parse("email-confirmacao-pagamento", vars);
      this.doSend(html, "Confirmação de Pagamento", pedido.getCliente().getEmail());
    } catch (Exception e) {
      Log.getLog().error(e.getMessage(), e);
    }
  }

  @Async
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void enviarEmailAgendamentoVoucher(final String id) {
    try {
      TimeUnit.SECONDS.sleep(15);
      Voucher entity = this.voucherJpaRepository.findById(id).orElse(null);
      VoucherRepresentation voucher = this.voucherJpaConverter.convert(entity);
      Map<String, Object> vars = Collections.singletonMap("voucher", voucher);
      String html = this.templateService.parse("email-agendamento-voucher", vars);

      Set<String> to = new HashSet<>();
      to.add(voucher.getPedido().getCliente().getEmail());
      if ((voucher.getPreCadastro() != null) && (!Strings.isNullOrEmpty(voucher.getPreCadastro().getEmailResp()))) {
        to.add(voucher.getPreCadastro().getEmailResp());
      }

      this.doSend(html, "Confirmação de Agendamento", Iterables.toArray(to, String.class));
    } catch (Exception e) {
      Log.getLog().error(e.getMessage(), e);
    }
  }

  private void doSend(final String html, final String subject, final String... destination) throws MessagingException {
    MimeMessage message = this.mailService.create();
    MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
    helper.setText(html, true);
    helper.setFrom(NotificacaoEmailServiceImpl.FROM);
    helper.setTo(destination);
    helper.setBcc(NotificacaoEmailServiceImpl.BCC);
    helper.setSubject(subject);
    this.mailService.send(message);
  }

}
