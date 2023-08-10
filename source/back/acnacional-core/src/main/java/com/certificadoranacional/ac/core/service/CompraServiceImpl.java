package com.certificadoranacional.ac.core.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.converter.PedidoJpaConverter;
import com.certificadoranacional.ac.core.converter.PedidoProdutoJpaConverter;
import com.certificadoranacional.ac.core.converter.VoucherJpaConverter;
import com.certificadoranacional.ac.core.model.ConvenioRepresentation;
import com.certificadoranacional.ac.core.model.PedidoProdutoRepresentation;
import com.certificadoranacional.ac.core.model.PedidoRepresentation;
import com.certificadoranacional.ac.jpa.entity.Cliente;
import com.certificadoranacional.ac.jpa.entity.Convenio;
import com.certificadoranacional.ac.jpa.entity.Pedido;
import com.certificadoranacional.ac.jpa.entity.PedidoProduto;
import com.certificadoranacional.ac.jpa.entity.Posto;
import com.certificadoranacional.ac.jpa.entity.Produto;
import com.certificadoranacional.ac.jpa.repository.ConvenioJpaRepository;
import com.certificadoranacional.ac.jpa.repository.PedidoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.PedidoProdutoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.PostoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.ProdutoJpaRepository;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

@Service
public class CompraServiceImpl implements CompraService {

  private static final BigDecimal V100 = BigDecimal.valueOf(100);

  @Autowired
  private PedidoJpaRepository pedidoJpaRepository;

  @Autowired
  private PedidoJpaConverter pedidoJpaConverter;

  @Autowired
  private PedidoProdutoJpaRepository pedidoProdutoJpaRepository;

  @Autowired
  private ProdutoJpaRepository produtoJpaRepository;

  @Autowired
  private PostoJpaRepository postoJpaRepository;

  @Autowired
  private PedidoProdutoJpaConverter pedidoProdutoJpaConverter;

  @Autowired
  private ConvenioJpaRepository convenioJpaRepository;

  @Autowired
  private VoucherJpaConverter voucherJpaConverter;

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private SessionService sessionService;

  @Autowired
  private PagSeguroService pagSeguroService;

  @Autowired
  private NotificacaoEmailService notificacaoEmailService;

  public CompraServiceImpl() {
    super();
  }

  private PedidoRepresentation toRepresentation(final Pedido obj) {
    PedidoRepresentation rep = this.pedidoJpaConverter.convert(obj);
    if (rep != null) {
      rep.setProdutoList(this.pedidoProdutoJpaConverter.convert(obj.getProdutoList()));
      rep.setVoucherList(this.voucherJpaConverter.convert(obj.getVoucherList()));
    }
    return rep;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PedidoRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Pedido obj = this.pedidoJpaRepository.findById(id).orElse(null);
    this.checkCliente(obj);
    return this.toRepresentation(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PedidoRepresentation begin() {
    String codigo = this.getCodigoPedido();
    Pedido entity = null;
    if (this.sessionService.isLogado()) {
      String idUsuario = this.sessionService.getIdUsuario();
      Cliente cliente = this.clienteService.getClienteByIdUsuario(idUsuario);
      Page<Pedido> page = this.pedidoJpaRepository.findByClienteIdAndSituacao(cliente.getId(), Pedido.SITUACAO_PENDENTE, SpringRepositoryHelper.ALL_PAGEABLE);
      List<Pedido> list = page.getContent();
      if (!list.isEmpty()) {
        entity = list.get(0);
      } else {
        entity = new Pedido();
        entity.setCliente(cliente);
        entity.setCodigo(codigo);
        entity.setData(new Date());
        entity.setSituacao(Pedido.SITUACAO_PENDENTE);
        entity.setValor(BigDecimal.ZERO);
        entity = this.pedidoJpaRepository.save(entity);
      }
    } else {
      entity = new Pedido();
      entity.setCodigo(codigo);
      entity.setData(new Date());
      entity.setSituacao(Pedido.SITUACAO_PENDENTE);
      entity.setValor(BigDecimal.ZERO);
      entity = this.pedidoJpaRepository.save(entity);
    }
    return this.toRepresentation(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PedidoRepresentation add(final PedidoProdutoRepresentation rep) {
    PedidoProduto entity = this.doAdd(rep);
    return this.toRepresentation(entity.getPedido());
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PedidoProdutoRepresentation addSimple(final PedidoProdutoRepresentation rep) {
    PedidoProduto entity = this.doAdd(rep);
    return this.pedidoProdutoJpaConverter.convert(entity);
  }

  private PedidoProduto doAdd(final PedidoProdutoRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Produto não pode ser nulo");
    Preconditions.checkArgument(rep.getPedido() != null, "Pedido não pode ser nulo");
    Preconditions.checkArgument(rep.getProduto() != null, "Produto não pode ser nulo");
    Preconditions.checkArgument(rep.getQuantidade() != null, "Quantidade não pode ser vazia");
    Preconditions.checkArgument(rep.getQuantidade().intValue() > 0, "Quantidade deve ser maior que zero");

    Pedido tmp0 = this.pedidoJpaRepository.findById(rep.getPedido().getId()).orElse(null);
    Preconditions.checkState(tmp0 != null, "Pedido não encontrado");
    Preconditions.checkState(tmp0.getSituacao().equals(Pedido.SITUACAO_PENDENTE), "Pedido inválido");

    Produto tmp1 = this.produtoJpaRepository.findById(rep.getProduto().getId()).orElse(null);
    Preconditions.checkState(tmp1 != null, "Produto não encontrado");

    Integer quantidade = rep.getQuantidade();
    BigDecimal value = tmp1.getValor().multiply(new BigDecimal(quantidade.intValue()));

    PedidoProduto entity = new PedidoProduto();
    entity.setPedido(tmp0);
    entity.setProduto(tmp1);
    entity.setQuantidade(quantidade);
    entity.setValor(value);

    entity = this.pedidoProdutoJpaRepository.save(entity);

    Pedido parent = entity.getPedido();
    parent.getProdutoList().add(entity);
    BigDecimal parentValue = new BigDecimal(0);
    for (PedidoProduto pd : parent.getProdutoList()) {
      BigDecimal bd = pd.getValor();
      parentValue = parentValue.add(bd);
    }
    this.checkCliente(parent);
    parent.setValor(parentValue);
    this.pedidoJpaRepository.save(parent);

    return entity;
  }

  @Override
  public PedidoRepresentation addConvenio(final String id, final ConvenioRepresentation rep) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Preconditions.checkArgument(rep != null, "Convênio não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getCodigo()), "Convênio não pode ser vazio");

    Pedido entity = this.pedidoJpaRepository.findById(id).orElse(null);
    Preconditions.checkState(entity != null, "Pedido não encontrado");
    Preconditions.checkState(entity.getSituacao().equals(Pedido.SITUACAO_PENDENTE), "Pedido inválido");

    Convenio sub = this.convenioJpaRepository.findByCodigo(rep.getCodigo());
    entity.setConvenio(sub);
    this.pedidoJpaRepository.save(entity);
    this.doCalcularValor(entity);

    return this.toRepresentation(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PedidoRepresentation update(final PedidoProdutoRepresentation rep) {
    PedidoProduto entity = this.doUpdate(rep);
    return this.toRepresentation(entity.getPedido());
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PedidoProdutoRepresentation updateSimple(final PedidoProdutoRepresentation rep) {
    PedidoProduto entity = this.doUpdate(rep);
    return this.pedidoProdutoJpaConverter.convert(entity);
  }

  private PedidoProduto doUpdate(final PedidoProdutoRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Produto não pode ser nulo");
    Preconditions.checkArgument(rep.getId() != null, "ID não pode ser nulo");
    Preconditions.checkArgument(rep.getQuantidade() != null, "Quantidade não pode ser vazia");
    Preconditions.checkArgument(rep.getQuantidade().intValue() > 0, "Quantidade deve ser maior que zero");

    PedidoProduto entity = this.pedidoProdutoJpaRepository.findById(rep.getId()).orElse(null);
    Preconditions.checkState(entity != null, "Produto não encontrado");

    Pedido parent = entity.getPedido();
    Preconditions.checkState(parent.getSituacao().equals(Pedido.SITUACAO_PENDENTE), "Pedido inválido");

    Integer quantidade = rep.getQuantidade();
    BigDecimal value = entity.getProduto().getValor().multiply(new BigDecimal(quantidade.intValue()));

    entity.setQuantidade(quantidade);
    entity.setValor(value);

    entity = this.pedidoProdutoJpaRepository.save(entity);

    BigDecimal parentValue = entity.getValor();
    for (PedidoProduto pd : parent.getProdutoList()) {
      if (!pd.getId().equals(entity.getId())) {
        BigDecimal bd = pd.getValor();
        parentValue = parentValue.add(bd);
      }
    }
    this.checkCliente(parent);
    parent.setValor(parentValue);
    this.pedidoJpaRepository.save(parent);

    return entity;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PedidoRepresentation remove(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");

    PedidoProduto entity = this.pedidoProdutoJpaRepository.findById(id).orElse(null);
    Preconditions.checkState(entity != null, "Pedido não encontrado");

    Pedido parent = entity.getPedido();
    Preconditions.checkState(parent.getSituacao().equals(Pedido.SITUACAO_PENDENTE), "Pedido inválido");

    this.pedidoProdutoJpaRepository.delete(entity);

    Iterator<PedidoProduto> iterator = parent.getProdutoList().iterator();
    while (iterator.hasNext()) {
      if (iterator.next().getId().equals(id)) {
        iterator.remove();
      }
    }

    BigDecimal parentValue = new BigDecimal(0);
    for (PedidoProduto pd : parent.getProdutoList()) {
      if (!pd.getId().equals(entity.getId())) {
        BigDecimal bd = pd.getValor();
        parentValue = parentValue.add(bd);
      }
    }
    this.checkCliente(parent);
    parent.setValor(parentValue);
    this.pedidoJpaRepository.save(parent);

    return this.toRepresentation(parent);
  }

  @Override
  public PedidoRepresentation removeConvenio(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");

    Pedido entity = this.pedidoJpaRepository.findById(id).orElse(null);
    Preconditions.checkState(entity != null, "Pedido não encontrado");
    Preconditions.checkState(entity.getSituacao().equals(Pedido.SITUACAO_PENDENTE), "Pedido inválido");

    entity.setConvenio(null);
    this.pedidoJpaRepository.save(entity);

    this.doCalcularValor(entity);

    return this.toRepresentation(entity);
  }

  private void doCalcularValor(final Pedido pedido) {
    BigDecimal desconto = null;

    if (pedido.getConvenio() != null) {
      desconto = pedido.getConvenio().getValor();
    }

    for (PedidoProduto sub : pedido.getProdutoList()) {
      Integer quantidade = sub.getQuantidade();
      BigDecimal valor = sub.getProduto().getValor().multiply(new BigDecimal(quantidade.intValue()));
      if (desconto != null) {
        BigDecimal percentualDesconto = desconto.divide(CompraServiceImpl.V100);
        BigDecimal valorDesconto = valor.multiply(percentualDesconto);
        valor = valor.subtract(valorDesconto);
      }
      
      sub.setValor(valor);
      this.pedidoProdutoJpaRepository.save(sub);
    }

    BigDecimal valorFrete = pedido.getValorFrete();

    BigDecimal valor = BigDecimal.ZERO;
    for (PedidoProduto pd : pedido.getProdutoList()) {
      BigDecimal bd = pd.getValor();
      valor = valor.add(bd);
    }

    valor = valor.add(valorFrete);

    pedido.setValor(valor);
    this.pedidoJpaRepository.save(pedido);
  }
  
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PedidoRepresentation end(final PedidoRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Pedido não pode ser nulo");
    Preconditions.checkArgument(rep.getId() != null, "ID não pode ser nulo");

    Pedido entity = this.pedidoJpaRepository.findById(rep.getId()).orElse(null);
    Preconditions.checkState(entity != null, "Pedido não encontrado");
    Preconditions.checkState(entity.getSituacao().equals(Pedido.SITUACAO_PENDENTE), "Pedido inválido");

    Posto posto = null;
    if ((rep.getPosto() != null) && (rep.getPosto().getId() != null)) {
      posto = this.postoJpaRepository.findById(rep.getPosto().getId()).orElse(null);
    }
    if (posto == null) {
      posto = this.postoJpaRepository.findByCodigo("ITECLIDERSIS");
    }

    this.checkCliente(entity);
    entity.setPosto(posto);

    entity.setData(new Date());
    entity.setSituacao(Pedido.SITUACAO_AGUARDANDO_PAGAMENTO);

    String code = this.pagSeguroService.send(entity);
    String url = this.pagSeguroService.getUrl(code);
    entity.setCodigoSolicitacaoPagamento(code);
    entity.setUrlSolicitacaoPagamento(url);

    String cep = rep.getCep();
      if (cep == null || cep.isEmpty()) {
        // Caso o cep não tenha sido informado, retornar um erro de requisição inválida
        throw new IllegalArgumentException("CEP não informado");
      }
  
        // // Aqui você pode realizar as ações necessárias com o valor do CEP
        // // Por exemplo, calcular o valor do frete com base no CEP
        // double valorFrete = calcularValorFrete(cep);
      
        // // Atualizar o valor do frete no objeto rep
        // rep.setValorFrete(valorFrete);

    entity = this.pedidoJpaRepository.save(entity);
    this.pedidoJpaRepository.flush();
    this.notificacaoEmailService.enviarEmailAguardandoPagamento(entity.getId());
    return this.toRepresentation(entity);

  }

  private void checkCliente(final Pedido entity) {
    String idUsuario = this.sessionService.getIdUsuario();
    if (!Strings.isNullOrEmpty(idUsuario)) {
      Cliente cliente = this.clienteService.getClienteByIdUsuario(idUsuario);
      entity.setCliente(cliente);
      this.pedidoJpaRepository.save(entity);
    }
  }

  private String getCodigoPedido() {
    String codigo = null;
    Pedido tmp = null;
    do {
      codigo = RandomStringUtils.randomAlphabetic(8).toUpperCase();
      tmp = this.pedidoJpaRepository.findByCodigo(codigo);
    } while (tmp != null);
    return codigo;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Boolean delete(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Pedido obj = this.pedidoJpaRepository.findById(id).orElse(null);
    if (obj != null) {
      Preconditions.checkState(!obj.getSituacao().equals(Pedido.SITUACAO_PENDENTE), "Pedido não pode ser excluído");
      this.pedidoJpaRepository.delete(obj);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<PedidoRepresentation> list() {
    String idUsuario = this.sessionService.getIdUsuario();
    Cliente cliente = this.clienteService.getClienteByIdUsuario(idUsuario);
    Page<Pedido> page = this.pedidoJpaRepository.findByClienteIdAndSituacao(cliente.getId(), Pedido.SITUACAO_PENDENTE, PageRequest.of(0, Integer.MAX_VALUE));
    return this.pedidoJpaConverter.convert(page);
  }

}
