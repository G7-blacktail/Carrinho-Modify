package com.certificadoranacional.ac.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.ConvenioRepresentation;
import com.certificadoranacional.ac.jpa.entity.Convenio;

@Component
public class ConvenioJpaConverter extends AbstractConverter<Convenio, ConvenioRepresentation> {

  @Autowired
  private DecimalConverter decimalConverter;

  public ConvenioJpaConverter() {
    super();
  }

  @Override
  public ConvenioRepresentation convert(final Convenio obj) {
    if (obj == null) {
      return null;
    }
    ConvenioRepresentation rep = new ConvenioRepresentation();
    rep.setAtivo(obj.getAtivo());
    rep.setCodigo(obj.getCodigo());
    rep.setId(obj.getId());
    rep.setNome(obj.getNome());
    if (obj.getValor() != null) {
      rep.setValor(Integer.toString(obj.getValor().intValue()));
    }
    return rep;
  }

  @Override
  public Convenio convertBack(final ConvenioRepresentation rep) {
    if (rep == null) {
      return null;
    }
    Convenio obj = new Convenio();
    obj.setAtivo(rep.getAtivo());
    obj.setCodigo(rep.getCodigo());
    obj.setId(rep.getId());
    obj.setNome(rep.getNome());
    obj.setValor(this.decimalConverter.convertBack(rep.getValor()));
    return obj;
  }

}
