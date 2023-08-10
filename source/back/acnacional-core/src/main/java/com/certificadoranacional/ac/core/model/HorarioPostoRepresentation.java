package com.certificadoranacional.ac.core.model;

import java.io.Serializable;

import com.certificadoranacional.ac.core.Constants;

public class HorarioPostoRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String id;

  private String data;

  private String evento;

  private Integer tipo;

  private Boolean disponivel;

  private HorarioRepresentation horario;

  private PostoRepresentation posto;

  public HorarioPostoRepresentation() {
    super();
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getData() {
    return this.data;
  }

  public void setData(final String data) {
    this.data = data;
  }

  public String getEvento() {
    return this.evento;
  }

  public void setEvento(final String evento) {
    this.evento = evento;
  }

  public Integer getTipo() {
    return this.tipo;
  }

  public void setTipo(final Integer tipo) {
    this.tipo = tipo;
  }

  public Boolean getDisponivel() {
    return this.disponivel;
  }

  public void setDisponivel(final Boolean disponivel) {
    this.disponivel = disponivel;
  }

  public HorarioRepresentation getHorario() {
    return this.horario;
  }

  public void setHorario(final HorarioRepresentation horario) {
    this.horario = horario;
  }

  public PostoRepresentation getPosto() {
    return this.posto;
  }

  public void setPosto(final PostoRepresentation posto) {
    this.posto = posto;
  }

}
