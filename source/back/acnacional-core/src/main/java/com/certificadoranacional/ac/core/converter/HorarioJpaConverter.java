package com.certificadoranacional.ac.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.HorarioRepresentation;
import com.certificadoranacional.ac.core.utils.DateUtils;
import com.certificadoranacional.ac.jpa.entity.Horario;

@Component
public class HorarioJpaConverter extends AbstractConverter<Horario, HorarioRepresentation> {

  @Autowired
  private LocalTimeConverter localTimeConverter;

  public HorarioJpaConverter() {
    super();
  }

  @Override
  public HorarioRepresentation convert(final Horario obj) {
    if (obj == null) {
      return null;
    }
    HorarioRepresentation rep = new HorarioRepresentation();
    rep.setCodigo(obj.getCodigo());
    rep.setDescricao(obj.getDescricao());
    rep.setFim(this.localTimeConverter.convert(DateUtils.toLocalTime(obj.getFim())));
    rep.setId(obj.getId());
    rep.setInicio(this.localTimeConverter.convert(DateUtils.toLocalTime(obj.getInicio())));
    return rep;
  }

  @Override
  public Horario convertBack(final HorarioRepresentation rep) {
    if (rep == null) {
      return null;
    }
    Horario obj = new Horario();
    obj.setCodigo(rep.getCodigo());
    obj.setDescricao(rep.getDescricao());
    obj.setFim(DateUtils.toTime(this.localTimeConverter.convertBack(rep.getFim())));
    obj.setId(obj.getId());
    obj.setInicio(DateUtils.toTime(this.localTimeConverter.convertBack(rep.getInicio())));
    return obj;
  }

}
