package com.certificadoranacional.ac.core.service;

import java.math.BigDecimal;

import com.certificadoranacional.ac.core.converter.PedidoJpaConverter;
import com.certificadoranacional.ac.core.converter.PedidoProdutoJpaConverter;
import com.certificadoranacional.ac.core.converter.VoucherJpaConverter;
import com.certificadoranacional.ac.core.model.PedidoRepresentation;
import com.certificadoranacional.ac.jpa.entity.Cliente;
import com.certificadoranacional.ac.jpa.entity.Pedido;
import com.certificadoranacional.ac.jpa.repository.PedidoJpaRepository;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService {

  @Autowired
  private PedidoJpaRepository pedidoJpaRepository;

  @Autowired
  private PedidoJpaConverter pedidoJpaConverter;

  @Autowired
  private PedidoProdutoJpaConverter pedidoProdutoJpaConverter;

  @Autowired
  private VoucherJpaConverter voucherJpaConverter;

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private SessionService sessionService;

  public PedidoServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PedidoRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Pedido obj = this.pedidoJpaRepository.findById(id).orElse(null);
    PedidoRepresentation rep = this.pedidoJpaConverter.convert(obj);
    if (rep != null) {
      rep.setProdutoList(this.pedidoProdutoJpaConverter.convert(obj.getProdutoList()));
      rep.setVoucherList(this.voucherJpaConverter.convert(obj.getVoucherList()));
    }
    return rep;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Boolean delete(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Pedido obj = this.pedidoJpaRepository.findById(id).orElse(null);
    // TODO Validar???
    if (obj != null) {
      this.pedidoJpaRepository.delete(obj);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<PedidoRepresentation> list(final String filter, final Boolean all, final Pageable pageable) {
    if (((all != null) && (all.booleanValue())) && (this.sessionService.isAdministrador())) {
      Page<Pedido> page = this.pedidoJpaRepository.findByValorGreaterThan(new BigDecimal(0), SpringRepositoryHelper.toSort(pageable, Direction.DESC, "data"));
      return this.pedidoJpaConverter.convert(page);
    }
    String idUsuario = this.sessionService.getIdUsuario();
    Cliente cliente = this.clienteService.getClienteByIdUsuario(idUsuario);
    Page<Pedido> page = this.pedidoJpaRepository.findByClienteIdAndValorGreaterThan(cliente.getId(), new BigDecimal(0),
        SpringRepositoryHelper.toSort(pageable, Direction.DESC, "data"));
    return this.pedidoJpaConverter.convert(page);
  }

}
