package com.certificadoranacional.ac.core.model;

import java.io.Serializable;

import com.certificadoranacional.ac.core.Constants;

public class VoucherRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String id;

  private String codigo;

  private Boolean ativo;

  private String dataConfirmacao;

  private String horaConfirmacao;

  private ClienteRepresentation cliente;

  private ProdutoRepresentation produto;

  private PedidoRepresentation pedido;

  private PedidoProdutoRepresentation pedidoProduto;

  private HorarioPostoRepresentation horarioPosto;

  private UsuarioRepresentation usuarioConfirmacao;

  private PreCadastroRepresentation preCadastro;

  public VoucherRepresentation() {
    super();
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCodigo() {
    return this.codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public Boolean getAtivo() {
    return this.ativo;
  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
  }

  public String getDataConfirmacao() {
    return this.dataConfirmacao;
  }

  public void setDataConfirmacao(String dataConfirmacao) {
    this.dataConfirmacao = dataConfirmacao;
  }

  public String getHoraConfirmacao() {
    return this.horaConfirmacao;
  }

  public void setHoraConfirmacao(final String horaConfirmacao) {
    this.horaConfirmacao = horaConfirmacao;
  }

  public ClienteRepresentation getCliente() {
    return this.cliente;
  }

  public void setCliente(ClienteRepresentation cliente) {
    this.cliente = cliente;
  }

  public ProdutoRepresentation getProduto() {
    return this.produto;
  }

  public void setProduto(ProdutoRepresentation produto) {
    this.produto = produto;
  }

  public PedidoRepresentation getPedido() {
    return this.pedido;
  }

  public void setPedido(PedidoRepresentation pedido) {
    this.pedido = pedido;
  }

  public PedidoProdutoRepresentation getPedidoProduto() {
    return this.pedidoProduto;
  }

  public void setPedidoProduto(final PedidoProdutoRepresentation pedidoProduto) {
    this.pedidoProduto = pedidoProduto;
  }

  public HorarioPostoRepresentation getHorarioPosto() {
    return this.horarioPosto;
  }

  public void setHorarioPosto(HorarioPostoRepresentation horarioPosto) {
    this.horarioPosto = horarioPosto;
  }

  public UsuarioRepresentation getUsuarioConfirmacao() {
    return this.usuarioConfirmacao;
  }

  public void setUsuarioConfirmacao(UsuarioRepresentation usuarioConfirmacao) {
    this.usuarioConfirmacao = usuarioConfirmacao;
  }

  public PreCadastroRepresentation getPreCadastro() {
    return this.preCadastro;
  }

  public void setPreCadastro(final PreCadastroRepresentation preCadastro) {
    this.preCadastro = preCadastro;
  }

}
