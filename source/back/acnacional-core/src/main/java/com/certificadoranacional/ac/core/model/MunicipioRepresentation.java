package com.certificadoranacional.ac.core.model;

import com.certificadoranacional.ac.core.Constants;

public class MunicipioRepresentation extends AuxRepresentation {

  private static final long serialVersionUID = Constants.VERSION;

  private UfRepresentation uf;

  public MunicipioRepresentation() {
    super();
  }

  public UfRepresentation getUf() {
    return this.uf;
  }

  public void setUf(final UfRepresentation uf) {
    this.uf = uf;
  }

}
