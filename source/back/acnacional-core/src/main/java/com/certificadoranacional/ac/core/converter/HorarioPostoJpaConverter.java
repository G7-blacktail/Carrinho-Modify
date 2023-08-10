package com.certificadoranacional.ac.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.HorarioPostoRepresentation;
import com.certificadoranacional.ac.core.utils.DateUtils;
import com.certificadoranacional.ac.jpa.entity.HorarioPosto;

@Component
public class HorarioPostoJpaConverter extends AbstractConverter<HorarioPosto, HorarioPostoRepresentation> {

  @Autowired
  private HorarioJpaConverter horarioJpaConverter;

  @Autowired
  private LocalDateConverter localDateConverter;

  @Autowired
  private PostoJpaConverter postoJpaConverter;

  public HorarioPostoJpaConverter() {
    super();
  }

  @Override
  public HorarioPostoRepresentation convert(final HorarioPosto obj) {
    if (obj == null) {
      return null;
    }
    HorarioPostoRepresentation rep = new HorarioPostoRepresentation();
    rep.setData(this.localDateConverter.convert(DateUtils.toLocalDate(obj.getData())));
    rep.setDisponivel(obj.getDisponivel());
    rep.setEvento(obj.getEvento());
    rep.setHorario(this.horarioJpaConverter.convert(obj.getHorario()));
    rep.setId(obj.getId());
    rep.setPosto(this.postoJpaConverter.convert(obj.getPosto()));
    rep.setTipo(obj.getTipo());
    return rep;
  }

  @Override
  public HorarioPosto convertBack(final HorarioPostoRepresentation rep) {
    if (rep == null) {
      return null;
    }
    HorarioPosto obj = new HorarioPosto();
    obj.setData(DateUtils.toDate(this.localDateConverter.convertBack(rep.getData())));
    obj.setDisponivel(rep.getDisponivel());
    obj.setEvento(rep.getEvento());
    obj.setHorario(this.horarioJpaConverter.convertBack(rep.getHorario()));
    obj.setId(rep.getId());
    obj.setPosto(this.postoJpaConverter.convertBack(rep.getPosto()));
    obj.setTipo(rep.getTipo());
    return obj;
  }

}
