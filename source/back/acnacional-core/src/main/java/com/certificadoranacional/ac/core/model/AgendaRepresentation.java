package com.certificadoranacional.ac.core.model;

import java.io.Serializable;
import java.util.List;

import com.certificadoranacional.ac.core.Constants;

public class AgendaRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String mes;

  private String ano;

  private String mesStr;

  private PostoRepresentation posto;

  private List<DiaAgendaRepresentation> diaList;

  public AgendaRepresentation() {
    super();
  }

  public String getMes() {
    return this.mes;
  }

  public void setMes(final String mes) {
    this.mes = mes;
  }

  public String getAno() {
    return this.ano;
  }

  public void setAno(final String ano) {
    this.ano = ano;
  }

  public String getMesStr() {
    return this.mesStr;
  }

  public void setMesStr(final String mesStr) {
    this.mesStr = mesStr;
  }

  public PostoRepresentation getPosto() {
    return this.posto;
  }

  public void setPosto(final PostoRepresentation posto) {
    this.posto = posto;
  }

  public List<DiaAgendaRepresentation> getDiaList() {
    return this.diaList;
  }

  public void setDiaList(final List<DiaAgendaRepresentation> diaList) {
    this.diaList = diaList;
  }

}
