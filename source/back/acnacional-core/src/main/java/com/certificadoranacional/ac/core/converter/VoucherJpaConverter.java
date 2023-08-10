package com.certificadoranacional.ac.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.VoucherRepresentation;
import com.certificadoranacional.ac.core.utils.DateUtils;
import com.certificadoranacional.ac.jpa.entity.Voucher;

@Component
public class VoucherJpaConverter extends AbstractConverter<Voucher, VoucherRepresentation> {

  @Autowired
  private ClienteJpaConverter clienteJpaConverter;

  @Autowired
  private HorarioPostoJpaConverter horarioPostoJpaConverter;

  @Autowired
  private ProdutoJpaConverter produtoJpaConverter;

  @Autowired
  private PedidoJpaConverter pedidoJpaConverter;
  
  @Autowired
  private PedidoProdutoJpaConverter pedidoProdutoJpaConverter;

  @Autowired
  private LocalDateConverter localDateConverter;

  @Autowired
  private LocalTimeConverter localTimeConverter;

  @Autowired
  private UsuarioJpaConverter usuarioJpaConverter;

  @Autowired
  private PreCadastroJpaConverter preCadastroJpaConverter;

  public VoucherJpaConverter() {
    super();
  }

  @Override
  public VoucherRepresentation convert(final Voucher obj) {
    if (obj == null) {
      return null;
    }
    VoucherRepresentation rep = new VoucherRepresentation();
    rep.setAtivo(obj.getAtivo());
    rep.setCodigo(obj.getCodigo());
    rep.setCliente(this.clienteJpaConverter.convert(obj.getCliente()));
    rep.setDataConfirmacao(this.localDateConverter.convert(DateUtils.toLocalDate(obj.getDataConfirmacao())));
    rep.setHoraConfirmacao(this.localTimeConverter.convert(DateUtils.toLocalTime(obj.getDataConfirmacao())));
    rep.setHorarioPosto(this.horarioPostoJpaConverter.convert(obj.getHorarioPosto()));
    rep.setId(obj.getId());
    rep.setPedido(this.pedidoJpaConverter.convert(obj.getPedido()));
    rep.setPedidoProduto(this.pedidoProdutoJpaConverter.convert(obj.getPedidoProduto()));
    rep.setPreCadastro(this.preCadastroJpaConverter.convert(obj.getPreCadastro()));
    rep.setProduto(this.produtoJpaConverter.convert(obj.getProduto()));
    rep.setUsuarioConfirmacao(this.usuarioJpaConverter.convert(obj.getUsuarioConfirmacao()));
    return rep;
  }

  @Override
  public Voucher convertBack(final VoucherRepresentation rep) {
    if (rep == null) {
      return null;
    }
    Voucher obj = new Voucher();
    obj.setAtivo(rep.getAtivo());
    obj.setCodigo(rep.getCodigo());
    obj.setCliente(this.clienteJpaConverter.convertBack(rep.getCliente()));
    rep.setHoraConfirmacao(this.localTimeConverter.convert(DateUtils.toLocalTime(obj.getDataConfirmacao())));
    obj.setHorarioPosto(this.horarioPostoJpaConverter.convertBack(rep.getHorarioPosto()));
    obj.setId(rep.getId());
    obj.setPedido(this.pedidoJpaConverter.convertBack(rep.getPedido()));
    obj.setPedidoProduto(this.pedidoProdutoJpaConverter.convertBack(rep.getPedidoProduto()));
    obj.setPreCadastro(this.preCadastroJpaConverter.convertBack(rep.getPreCadastro()));
    obj.setProduto(this.produtoJpaConverter.convertBack(rep.getProduto()));
    return obj;
  }

}
