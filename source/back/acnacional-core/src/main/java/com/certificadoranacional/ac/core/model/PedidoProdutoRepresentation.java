package com.certificadoranacional.ac.core.model;

import java.io.Serializable;

import com.certificadoranacional.ac.core.Constants;

public class PedidoProdutoRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String id;

  private Integer quantidade;

  private String valor;

  private PedidoRepresentation pedido;

  private ProdutoRepresentation produto;

  public PedidoProdutoRepresentation() {
    super();
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public Integer getQuantidade() {
    return this.quantidade;
  }

  public void setQuantidade(final Integer quantidade) {
    this.quantidade = quantidade;
  }

  public String getValor() {
    return this.valor;
  }

  public void setValor(final String valor) {
    this.valor = valor;
  }

  public PedidoRepresentation getPedido() {
    return this.pedido;
  }

  public void setPedido(final PedidoRepresentation pedido) {
    this.pedido = pedido;
  }

  public ProdutoRepresentation getProduto() {
    return this.produto;
  }

  public void setProduto(final ProdutoRepresentation produto) {
    this.produto = produto;
  }

}
