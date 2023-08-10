package com.certificadoranacional.ac.core.model;

import java.io.Serializable;

import com.certificadoranacional.ac.core.Constants;

public class ItemAgendaRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private Integer numero;

  private HorarioPostoRepresentation horarioPosto;

  private VoucherRepresentation voucher;

  public ItemAgendaRepresentation() {
    super();
  }

  public Integer getNumero() {
    return this.numero;
  }

  public void setNumero(final Integer numero) {
    this.numero = numero;
  }

  public HorarioPostoRepresentation getHorarioPosto() {
    return this.horarioPosto;
  }

  public void setHorarioPosto(final HorarioPostoRepresentation horarioPosto) {
    this.horarioPosto = horarioPosto;
  }

  public VoucherRepresentation getVoucher() {
    return this.voucher;
  }

  public void setVoucher(final VoucherRepresentation voucher) {
    this.voucher = voucher;
  }

}
