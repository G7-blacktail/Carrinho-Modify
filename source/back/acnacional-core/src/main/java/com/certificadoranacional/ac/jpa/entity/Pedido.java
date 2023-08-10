package com.certificadoranacional.ac.jpa.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_pedido")
public class Pedido extends AbstractEntity {

  public static final Integer SITUACAO_CANCELADO = Integer.valueOf(0);

  public static final Integer SITUACAO_PENDENTE = Integer.valueOf(1);

  public static final Integer SITUACAO_AGUARDANDO_PAGAMENTO = Integer.valueOf(2);

  public static final Integer SITUACAO_PAGO = Integer.valueOf(3);

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  @Column(name = "id_pedido", length = 100)
  private String id;

  @Column(name = "cd_pedido", length = 50, nullable = false)
  @NotNull
  @Size(min = 1, max = 50)
  private String codigo;

  @Column(name = "dt_pedido", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @NotNull
  private Date data;

  @Column(name = "vl_pedido", nullable = false)
  @NotNull
  private BigDecimal valor;

  @Column(name = "tp_situacao", nullable = false)
  @NotNull
  private Integer situacao;

  @Column(name = "cd_solicitacao_pagamento", length = 100, nullable = true)
  @Size(max = 100)
  private String codigoSolicitacaoPagamento;

  @Column(name = "cd_transacao_pagamento", length = 100, nullable = true)
  @Size(max = 100)
  private String codigoTransacaoPagamento;

  @Column(name = "ds_url_solicitacao_pagamento", length = 200, nullable = true)
  @Size(max = 200)
  private String urlSolicitacaoPagamento;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", nullable = true)
  private Cliente cliente;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "id_posto", referencedColumnName = "id_posto", nullable = true)
  private Posto posto;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "id_convenio", referencedColumnName = "id_convenio", nullable = true)
  private Convenio convenio;

  @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
  private List<PedidoProduto> produtoList;

  @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
  private List<Voucher> voucherList;

  @Column(name = "vl_frete", nullable = true)
  private BigDecimal valorFrete;

  public Pedido() {
    super();
  }

  public Pedido(final String id) {
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

  public String getCodigo() {
    return this.codigo;
  }

  public void setCodigo(final String codigo) {
    this.codigo = codigo;
  }

  public Date getData() {
    return this.data;
  }

  public void setData(final Date data) {
    this.data = data;
  }

  public BigDecimal getValor() {
    return this.valor;
  }

  public void setValor(final BigDecimal valor) {
    this.valor = valor;
  }

  public Integer getSituacao() {
    return this.situacao;
  }

  public void setSituacao(final Integer situacao) {
    this.situacao = situacao;
  }

  public String getCodigoSolicitacaoPagamento() {
    return this.codigoSolicitacaoPagamento;
  }

  public void setCodigoSolicitacaoPagamento(final String codigoSolicitacaoPagamento) {
    this.codigoSolicitacaoPagamento = codigoSolicitacaoPagamento;
  }

  public String getCodigoTransacaoPagamento() {
    return this.codigoTransacaoPagamento;
  }

  public void setCodigoTransacaoPagamento(final String codigoTransacaoPagamento) {
    this.codigoTransacaoPagamento = codigoTransacaoPagamento;
  }

  public String getUrlSolicitacaoPagamento() {
    return this.urlSolicitacaoPagamento;
  }

  public void setUrlSolicitacaoPagamento(final String urlSolicitacaoPagamento) {
    this.urlSolicitacaoPagamento = urlSolicitacaoPagamento;
  }

  public Cliente getCliente() {
    return this.cliente;
  }

  public void setCliente(final Cliente cliente) {
    this.cliente = cliente;
  }

  public Posto getPosto() {
    return this.posto;
  }

  public void setPosto(final Posto posto) {
    this.posto = posto;
  }

  public Convenio getConvenio() {
    return this.convenio;
  }

  public void setConvenio(final Convenio convenio) {
    this.convenio = convenio;
  }

  public List<PedidoProduto> getProdutoList() {
    return this.produtoList;
  }

  public void setProdutoList(final List<PedidoProduto> produtoList) {
    this.produtoList = produtoList;
  }

  public List<Voucher> getVoucherList() {
    return this.voucherList;
  }

  public void setVoucherList(final List<Voucher> voucherList) {
    this.voucherList = voucherList;
  }

  public BigDecimal getValorFrete() {
    return this.valorFrete;
  }
  
  public void setValorFrete(final BigDecimal valorFrete) {
    this.valorFrete = valorFrete;
  }

}
