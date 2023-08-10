package com.certificadoranacional.ac.core.model;

import java.io.Serializable;

import com.certificadoranacional.ac.core.Constants;

public class UsuarioRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String id;

  private String nome;

  private String email;

  private Boolean ativo;

  public UsuarioRepresentation() {
    super();
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(final String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public Boolean getAtivo() {
    return this.ativo;
  }

  public void setAtivo(final Boolean ativo) {
    this.ativo = ativo;
  }

}
