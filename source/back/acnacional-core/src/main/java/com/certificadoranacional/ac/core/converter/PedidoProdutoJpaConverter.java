package com.certificadoranacional.ac.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.PedidoProdutoRepresentation;
import com.certificadoranacional.ac.jpa.entity.PedidoProduto;

@Component
public class PedidoProdutoJpaConverter extends AbstractConverter<PedidoProduto, PedidoProdutoRepresentation> {

  @Autowired
  private ProdutoJpaConverter produtoJpaConverter;

  @Autowired
  private PedidoJpaConverter pedidoJpaConverter;

  @Autowired
  private DecimalConverter decimalConverter;

  public PedidoProdutoJpaConverter() {
    super();
  }

  @Override
  public PedidoProdutoRepresentation convert(final PedidoProduto obj) {
    if (obj == null) {
      return null;
    }
    PedidoProdutoRepresentation rep = new PedidoProdutoRepresentation();
    rep.setId(obj.getId());
    rep.setPedido(this.pedidoJpaConverter.convert(obj.getPedido()));
    rep.setProduto(this.produtoJpaConverter.convert(obj.getProduto()));
    rep.setQuantidade(obj.getQuantidade());
    rep.setValor(this.decimalConverter.convert(obj.getValor()));
    return rep;
  }

  @Override
  public PedidoProduto convertBack(final PedidoProdutoRepresentation rep) {
    if (rep == null) {
      return null;
    }
    PedidoProduto obj = new PedidoProduto();
    obj.setId(rep.getId());
    obj.setPedido(this.pedidoJpaConverter.convertBack(rep.getPedido()));
    obj.setProduto(this.produtoJpaConverter.convertBack(rep.getProduto()));
    obj.setQuantidade(rep.getQuantidade());
    obj.setValor(this.decimalConverter.convertBack(rep.getValor()));
    return obj;
  }

}
