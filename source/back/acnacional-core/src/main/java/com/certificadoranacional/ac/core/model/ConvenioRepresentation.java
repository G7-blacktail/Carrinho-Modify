package com.certificadoranacional.ac.core.model;

import java.io.Serializable;

import com.certificadoranacional.ac.core.Constants;

public class ConvenioRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String id;

  private String codigo;

  private String nome;

  private String valor;

  private Boolean ativo;

  private UsuarioRepresentation usuario;

  public ConvenioRepresentation() {
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

  public String getValor() {
    return this.valor;
  }

  public void setValor(final String valor) {
    this.valor = valor;
  }

  public Boolean getAtivo() {
    return this.ativo;
  }

  public void setAtivo(final Boolean ativo) {
    this.ativo = ativo;
  }

  public UsuarioRepresentation getUsuario() {
    return this.usuario;
  }

  public void setUsuario(final UsuarioRepresentation usuario) {
    this.usuario = usuario;
  }

}
