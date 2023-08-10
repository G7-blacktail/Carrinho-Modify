package com.certificadoranacional.ac.core.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.converter.VoucherJpaConverter;
import com.certificadoranacional.ac.core.model.VoucherRepresentation;
import com.certificadoranacional.ac.jpa.entity.Cliente;
import com.certificadoranacional.ac.jpa.entity.HorarioPosto;
import com.certificadoranacional.ac.jpa.entity.Pedido;
import com.certificadoranacional.ac.jpa.entity.Usuario;
import com.certificadoranacional.ac.jpa.entity.Voucher;
import com.certificadoranacional.ac.jpa.repository.ClienteJpaRepository;
import com.certificadoranacional.ac.jpa.repository.HorarioPostoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.PedidoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.ProdutoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.UsuarioJpaRepository;
import com.certificadoranacional.ac.jpa.repository.VoucherJpaRepository;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

@Service
public class VoucherServiceImpl implements VoucherService {

  @Autowired
  private VoucherJpaRepository voucherJpaRepository;

  @Autowired
  private VoucherJpaConverter voucherJpaConverter;

  @Autowired
  private ClienteJpaRepository clienteJpaRepository;

  @Autowired
  private ProdutoJpaRepository produtoJpaRepository;

  @Autowired
  private PedidoJpaRepository pedidoJpaRepository;

  @Autowired
  private HorarioPostoJpaRepository horarioPostoJpaRepository;

  @Autowired
  private UsuarioJpaRepository usuarioJpaRepository;

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private SessionService sessionService;

  @Autowired
  private NotificacaoEmailService notificacaoEmailService;

  public VoucherServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public VoucherRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Voucher obj = this.voucherJpaRepository.findById(id).orElse(null);
    return this.voucherJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public VoucherRepresentation save(final VoucherRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Voucher não pode ser nulo");
    Preconditions.checkArgument(rep.getCliente() != null, "Cliente não pode ser nulo");
    Preconditions.checkArgument(rep.getProduto() != null, "Produto não pode ser nulo");

    Voucher tmp = this.voucherJpaRepository.findByCodigo(rep.getCodigo());
    Preconditions.checkState(tmp == null, "Já existe um voucher com esse código");

    Voucher entity = this.voucherJpaConverter.convertBack(rep);
    entity.setCliente(this.clienteJpaRepository.getById(entity.getCliente().getId()));
    entity.setProduto(this.produtoJpaRepository.getById(entity.getProduto().getId()));
    if (entity.getPedido() != null) {
      entity.setPedido(this.pedidoJpaRepository.getById(entity.getPedido().getId()));
    }
    entity = this.voucherJpaRepository.save(entity);
    return this.voucherJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public VoucherRepresentation update(final VoucherRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Voucher não pode ser nulo");
    Preconditions.checkArgument(rep.getCliente() != null, "Cliente não pode ser nulo");
    Preconditions.checkArgument(rep.getProduto() != null, "Produto não pode ser nulo");

    Voucher tmp = this.voucherJpaConverter.convertBack(rep);
    Voucher entity = this.voucherJpaRepository.findById(rep.getId()).orElse(null);
    Preconditions.checkState(entity != null, "Voucher não encontrado");
    entity.setAtivo(tmp.getAtivo());

    entity = this.voucherJpaRepository.save(entity);
    return this.voucherJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public VoucherRepresentation schedule(final VoucherRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Voucher não pode ser nulo");

    Voucher voucher = this.voucherJpaRepository.findById(rep.getId()).orElse(null);
    Preconditions.checkState(voucher != null, "Voucher não encontrado");
    Preconditions.checkState(voucher.getAtivo().booleanValue(), "Voucher inválido");

    String idUsuario = this.sessionService.getIdUsuario();
    if (!this.sessionService.isAdministrador()) {
      Cliente cliente = this.clienteService.getClienteByIdUsuario(idUsuario);
      Pedido pedido = voucher.getPedido();
      Preconditions.checkState(pedido.getCliente().getId().equals(cliente.getId()), "Pedido não pertence ao cliente");
    }

    HorarioPosto tmp = voucher.getHorarioPosto();
    if (tmp != null) {
      tmp.setDisponivel(Boolean.TRUE);
      tmp.setEvento(null);
      tmp.setTipo(HorarioPosto.TIPO_DISPONIVEL);
      this.horarioPostoJpaRepository.save(tmp);
    }

    if ((rep.getHorarioPosto() != null) && (rep.getHorarioPosto().getId() != null)) {
      HorarioPosto horario = this.horarioPostoJpaRepository.findById(rep.getHorarioPosto().getId()).orElse(null);
      Preconditions.checkState(horario != null, "Horário não encontrado");
      Preconditions.checkState(horario.getDisponivel().booleanValue(), "Horário indisponível");

      HorarioPostoServiceHelper.check(horario);

      voucher.setHorarioPosto(horario);

      horario.setEvento(voucher.getCliente().getNome());
      horario.setDisponivel(Boolean.FALSE);
      horario.setTipo(HorarioPosto.TIPO_VOUCHER);
      this.horarioPostoJpaRepository.save(horario);

    } else {
      voucher.setHorarioPosto(null);
    }

    voucher = this.voucherJpaRepository.save(voucher);
    this.voucherJpaRepository.flush();
    if (voucher.getHorarioPosto() != null) {
      this.notificacaoEmailService.enviarEmailAgendamentoVoucher(voucher.getId());
    }
    return this.voucherJpaConverter.convert(voucher);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public VoucherRepresentation done(final VoucherRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Voucher não pode ser nulo");

    Voucher voucher = this.voucherJpaRepository.findById(rep.getId()).orElse(null);
    Preconditions.checkState(voucher != null, "Voucher não encontrado");
    Preconditions.checkState(voucher.getAtivo().booleanValue(), "Voucher inválido");

    String idUsuario = this.sessionService.getIdUsuario();
    if (!this.sessionService.isAdministrador()) {
      Cliente cliente = this.clienteService.getClienteByIdUsuario(idUsuario);
      Pedido pedido = voucher.getPedido();
      Preconditions.checkState(pedido.getCliente().getId().equals(cliente.getId()), "Pedido não pertence ao cliente");
    }

    Usuario usuario = this.usuarioJpaRepository.findById(this.sessionService.getIdUsuario()).orElse(null);

    voucher.setAtivo(Boolean.FALSE);
    voucher.setDataConfirmacao(new Date());
    voucher.setUsuarioConfirmacao(usuario);
    voucher = this.voucherJpaRepository.save(voucher);

    return this.voucherJpaConverter.convert(voucher);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Boolean delete(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Voucher obj = this.voucherJpaRepository.findById(id).orElse(null);
    if (obj != null) {
      this.voucherJpaRepository.delete(obj);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<VoucherRepresentation> list(final String filter, final Pageable pageable) {
    // TODO Melhorar esse metodo
    Page<Voucher> page = null;
    if (!Strings.isNullOrEmpty(filter)) {
      String str = MoreObjects.firstNonNull(filter, "");
      page = this.voucherJpaRepository.findByClienteId(str, pageable);
    } else {
      page = this.voucherJpaRepository.findAll(pageable);
    }
    return this.voucherJpaConverter.convert(page);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<VoucherRepresentation> listByStatus(final String filter, final Boolean status, final Pageable pageable) {
    String idUsuario = this.sessionService.getIdUsuario();
    Cliente cliente = this.clienteService.getClienteByIdUsuario(idUsuario);
    String idCliente = cliente.getId();
    Page<Voucher> page = this.voucherJpaRepository.findByClienteIdAndAtivo(idCliente, status, SpringRepositoryHelper.toSort(pageable, "pedido.data"));
    return this.voucherJpaConverter.convert(page);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<VoucherRepresentation> listByIdPedido(final String idPedido, final String filter, final Pageable pageable) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(idPedido), "ID não pode ser vazio");
    Pedido pedido = this.pedidoJpaRepository.findById(idPedido).orElse(null);
    Preconditions.checkArgument(pedido != null, "Pedido não encontrado");

    String idUsuario = this.sessionService.getIdUsuario();
    if (!this.sessionService.isAdministrador()) {
      Cliente cliente = this.clienteService.getClienteByIdUsuario(idUsuario);
      Preconditions.checkState(pedido.getCliente().getId().equals(cliente.getId()), "Pedido não pertence ao cliente");
    }

    Page<Voucher> page = this.voucherJpaRepository.findByPedidoIdOrderByCodigoAsc(idPedido, pageable);
    return this.voucherJpaConverter.convert(page);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<VoucherRepresentation> listByData(final String data, final Pageable pageable) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(data), "Data não pode ser vazio");
    Page<Voucher> page = this.voucherJpaRepository.findByHorarioPostoDataTx(data, pageable);
    return this.voucherJpaConverter.convert(page);
  }

}
