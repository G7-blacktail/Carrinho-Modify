package com.certificadoranacional.ac.core.model;

import java.io.Serializable;

import com.certificadoranacional.ac.core.Constants;

public class FuncionarioRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String id;

  private Integer tipo;

  private PostoRepresentation posto;

  private UsuarioRepresentation usuario;

  public FuncionarioRepresentation() {
    super();
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public Integer getTipo() {
    return this.tipo;
  }

  public void setTipo(final Integer tipo) {
    this.tipo = tipo;
  }

  public PostoRepresentation getPosto() {
    return this.posto;
  }

  public void setPosto(final PostoRepresentation posto) {
    this.posto = posto;
  }

  public UsuarioRepresentation getUsuario() {
    return this.usuario;
  }

  public void setUsuario(final UsuarioRepresentation usuario) {
    this.usuario = usuario;
  }

}
