package com.certificadoranacional.ac.core.model;

import java.io.Serializable;

import com.certificadoranacional.ac.core.Constants;

public class HorarioRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String id;

  private String codigo;

  private String descricao;

  private String inicio;

  private String fim;

  public HorarioRepresentation() {
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

  public String getDescricao() {
    return this.descricao;
  }

  public void setDescricao(final String descricao) {
    this.descricao = descricao;
  }

  public String getInicio() {
    return this.inicio;
  }

  public void setInicio(final String inicio) {
    this.inicio = inicio;
  }

  public String getFim() {
    return this.fim;
  }

  public void setFim(final String fim) {
    this.fim = fim;
  }

}
