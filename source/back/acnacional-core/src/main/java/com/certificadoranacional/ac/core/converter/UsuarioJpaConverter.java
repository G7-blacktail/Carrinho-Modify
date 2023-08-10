package com.certificadoranacional.ac.core.converter;

import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.UsuarioRepresentation;
import com.certificadoranacional.ac.jpa.entity.Usuario;

@Component
public class UsuarioJpaConverter extends AbstractConverter<Usuario, UsuarioRepresentation> {

  public UsuarioJpaConverter() {
    super();
  }

  @Override
  public UsuarioRepresentation convert(final Usuario obj) {
    if (obj == null) {
      return null;
    }
    UsuarioRepresentation rep = new UsuarioRepresentation();
    rep.setAtivo(obj.getAtivo());
    rep.setEmail(obj.getEmail());
    rep.setId(obj.getId());
    rep.setNome(obj.getNome());
    return rep;
  }

  @Override
  public Usuario convertBack(final UsuarioRepresentation rep) {
    if (rep == null) {
      return null;
    }
    Usuario obj = new Usuario();
    obj.setAtivo(rep.getAtivo());
    obj.setEmail(rep.getEmail());
    obj.setId(rep.getId());
    obj.setNome(rep.getNome());
    return obj;
  }

}
