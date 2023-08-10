package com.certificadoranacional.ac.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.MunicipioRepresentation;
import com.certificadoranacional.ac.jpa.entity.Municipio;

@Component
public class MunicipioJpaConverter extends AuxJpaConverter<Municipio, MunicipioRepresentation> {

  @Autowired
  private UfJpaConverter ufJpaConverter;

  public MunicipioJpaConverter() {
    super();
  }

  @Override
  public MunicipioRepresentation convert(final Municipio obj) {
    MunicipioRepresentation rep = super.convert(obj);
    if (rep != null) {
      rep.setUf(this.ufJpaConverter.convert(obj.getUf()));
    }
    return rep;
  }

  @Override
  public Municipio convertBack(final MunicipioRepresentation rep) {
    Municipio obj = super.convertBack(rep);
    if (obj != null) {
      obj.setUf(this.ufJpaConverter.convertBack(rep.getUf()));
    }
    return obj;
  }

}
