package com.certificadoranacional.ac.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.FuncionarioRepresentation;
import com.certificadoranacional.ac.jpa.entity.Funcionario;

@Component
public class FuncionarioJpaConverter extends AbstractConverter<Funcionario, FuncionarioRepresentation> {

  @Autowired
  private PostoJpaConverter postoJpaConverter;

  @Autowired
  private UsuarioJpaConverter usuarioJpaConverter;

  public FuncionarioJpaConverter() {
    super();
  }

  @Override
  public FuncionarioRepresentation convert(final Funcionario obj) {
    if (obj == null) {
      return null;
    }
    FuncionarioRepresentation rep = new FuncionarioRepresentation();
    rep.setId(obj.getId());
    rep.setPosto(this.postoJpaConverter.convert(obj.getPosto()));
    rep.setTipo(obj.getTipo());
    rep.setUsuario(this.usuarioJpaConverter.convert(obj.getUsuario()));
    return rep;
  }

  @Override
  public Funcionario convertBack(final FuncionarioRepresentation rep) {
    if (rep == null) {
      return null;
    }
    Funcionario obj = new Funcionario();
    obj.setId(rep.getId());
    obj.setPosto(this.postoJpaConverter.convertBack(rep.getPosto()));
    obj.setTipo(rep.getTipo());
    obj.setUsuario(this.usuarioJpaConverter.convertBack(rep.getUsuario()));
    return obj;
  }

}
