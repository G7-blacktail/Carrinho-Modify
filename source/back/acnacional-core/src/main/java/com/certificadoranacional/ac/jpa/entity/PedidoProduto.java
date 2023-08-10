package com.certificadoranacional.ac.jpa.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_pedido_produto")
public class PedidoProduto extends AbstractEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  @Column(name = "id_pedido_produto", length = 100)
  private String id;

  @Column(name = "nr_quantidade", nullable = false)
  @NotNull
  private Integer quantidade;

  @Column(name = "vl_total", nullable = false)
  @NotNull
  private BigDecimal valor;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_pedido", referencedColumnName = "id_pedido", nullable = false)
  @NotNull
  private Pedido pedido;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_produto", referencedColumnName = "id_produto", nullable = false)
  @NotNull
  private Produto produto;

  public PedidoProduto() {
    super();
  }

  public PedidoProduto(final String id) {
    super(id);
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public void setId(final String id) {
    this.id = id;
  }

  public Integer getQuantidade() {
    return this.quantidade;
  }

  public void setQuantidade(final Integer quantidade) {
    this.quantidade = quantidade;
  }

  public BigDecimal getValor() {
    return this.valor;
  }

  public void setValor(final BigDecimal valor) {
    this.valor = valor;
  }

  public Pedido getPedido() {
    return this.pedido;
  }

  public void setPedido(final Pedido pedido) {
    this.pedido = pedido;
  }

  public Produto getProduto() {
    return this.produto;
  }

  public void setProduto(final Produto produto) {
    this.produto = produto;
  }

}
