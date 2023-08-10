package com.certificadoranacional.ac.core.model;

import java.io.Serializable;
import java.util.List;

import com.certificadoranacional.ac.core.Constants;

public class DiaAgendaRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String dia;

  private String data;

  private String diaStr;

  private List<ItemAgendaRepresentation> itemList;

  public DiaAgendaRepresentation() {
    super();
  }

  public String getDia() {
    return this.dia;
  }

  public void setDia(final String dia) {
    this.dia = dia;
  }

  public String getData() {
    return this.data;
  }

  public void setData(final String data) {
    this.data = data;
  }

  public String getDiaStr() {
    return this.diaStr;
  }

  public void setDiaStr(final String diaStr) {
    this.diaStr = diaStr;
  }

  public List<ItemAgendaRepresentation> getItemList() {
    return this.itemList;
  }

  public void setItemList(final List<ItemAgendaRepresentation> itemList) {
    this.itemList = itemList;
  }

}
