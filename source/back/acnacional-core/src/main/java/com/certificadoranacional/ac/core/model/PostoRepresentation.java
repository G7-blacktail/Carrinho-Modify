package com.certificadoranacional.ac.core.model;

import java.io.Serializable;

import com.certificadoranacional.ac.core.Constants;

public class PostoRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String id;

  private String codigo;

  private String nome;

  private String responsavel;

  private String email;

  private String endereco;

  private String complemento;

  private String numero;

  private String bairro;

  private String cep;

  private String telefone;

  private String mapa;

  private Boolean ativo;

  private UfRepresentation uf;

  private MunicipioRepresentation municipio;

  public PostoRepresentation() {
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

  public String getNome() {
    return this.nome;
  }

  public void setNome(final String nome) {
    this.nome = nome;
  }

  public String getResponsavel() {
    return this.responsavel;
  }

  public void setResponsavel(final String responsavel) {
    this.responsavel = responsavel;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getEndereco() {
    return this.endereco;
  }

  public void setEndereco(final String endereco) {
    this.endereco = endereco;
  }

  public String getComplemento() {
    return this.complemento;
  }

  public void setComplemento(final String complemento) {
    this.complemento = complemento;
  }

  public String getNumero() {
    return this.numero;
  }

  public void setNumero(final String numero) {
    this.numero = numero;
  }

  public String getBairro() {
    return this.bairro;
  }

  public void setBairro(final String bairro) {
    this.bairro = bairro;
  }

  public String getCep() {
    return this.cep;
  }

  public void setCep(final String cep) {
    this.cep = cep;
  }

  public String getTelefone() {
    return this.telefone;
  }

  public void setTelefone(final String telefone) {
    this.telefone = telefone;
  }

  public String getMapa() {
    return this.mapa;
  }

  public void setMapa(final String mapa) {
    this.mapa = mapa;
  }

  public Boolean getAtivo() {
    return this.ativo;
  }

  public void setAtivo(final Boolean ativo) {
    this.ativo = ativo;
  }

  public UfRepresentation getUf() {
    return this.uf;
  }

  public void setUf(final UfRepresentation uf) {
    this.uf = uf;
  }

  public MunicipioRepresentation getMunicipio() {
    return this.municipio;
  }

  public void setMunicipio(final MunicipioRepresentation municipio) {
    this.municipio = municipio;
  }

}
