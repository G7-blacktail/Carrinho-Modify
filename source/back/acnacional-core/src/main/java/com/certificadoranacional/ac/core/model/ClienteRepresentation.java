package com.certificadoranacional.ac.core.model;

import java.io.Serializable;

import com.certificadoranacional.ac.core.Constants;

public class ClienteRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String id;

  private String nome;

  private String documento;

  private String email;

  private UsuarioRepresentation usuario;

  public ClienteRepresentation() {
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

  public String getDocumento() {
    return this.documento;
  }

  public void setDocumento(final String documento) {
    this.documento = documento;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public UsuarioRepresentation getUsuario() {
    return this.usuario;
  }

  public void setUsuario(final UsuarioRepresentation usuario) {
    this.usuario = usuario;
  }

}
