package com.certificadoranacional.ac.core.model;

import java.io.Serializable;

import com.certificadoranacional.ac.core.Constants;

public class ProdutoRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String id;

  private String codigo;

  private String codigoSerpro;

  private Integer ordem;

  private String nome;

  private String descricao;

  private String imagem;

  private Integer validade;

  private String valor;

  private String valorDesconto;

  private String valorReferencia;

  private Boolean ativo;

  private TipoProdutoRepresentation tipo;

  private GrupoProdutoRepresentation grupo;

  private SubGrupoProdutoRepresentation subGrupo;

  public ProdutoRepresentation() {
    super();
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getCodigo() {
    return this.codigo;
  }

  public void setCodigo(final String codigo) {
    this.codigo = codigo;
  }

  public String getCodigoSerpro() {
    return this.codigoSerpro;
  }

  public void setCodigoSerpro(final String codigoSerpro) {
    this.codigoSerpro = codigoSerpro;
  }

  public Integer getOrdem() {
    return this.ordem;
  }

  public void setOrdem(final Integer ordem) {
    this.ordem = ordem;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(final String nome) {
    this.nome = nome;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public void setDescricao(final String descricao) {
    this.descricao = descricao;
  }

  public String getImagem() {
    return this.imagem;
  }

  public void setImagem(final String imagem) {
    this.imagem = imagem;
  }

  public Integer getValidade() {
    return this.validade;
  }

  public void setValidade(final Integer validade) {
    this.validade = validade;
  }

  public String getValor() {
    return this.valor;
  }

  public void setValor(final String valor) {
    this.valor = valor;
  }

  public String getValorDesconto() {
    return this.valorDesconto;
  }

  public void setValorDesconto(final String valorDesconto) {
    this.valorDesconto = valorDesconto;
  }

  public String getValorReferencia() {
    return this.valorReferencia;
  }

  public void setValorReferencia(final String valorReferencia) {
    this.valorReferencia = valorReferencia;
  }

  public Boolean getAtivo() {
    return this.ativo;
  }

  public void setAtivo(final Boolean ativo) {
    this.ativo = ativo;
  }

  public TipoProdutoRepresentation getTipo() {
    return this.tipo;
  }

  public void setTipo(final TipoProdutoRepresentation tipo) {
    this.tipo = tipo;
  }

  public GrupoProdutoRepresentation getGrupo() {
    return this.grupo;
  }

  public void setGrupo(final GrupoProdutoRepresentation grupo) {
    this.grupo = grupo;
  }

  public SubGrupoProdutoRepresentation getSubGrupo() {
    return this.subGrupo;
  }

  public void setSubGrupo(final SubGrupoProdutoRepresentation subGrupo) {
    this.subGrupo = subGrupo;
  }

}
