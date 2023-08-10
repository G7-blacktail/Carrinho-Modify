package com.certificadoranacional.ac.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.ClienteRepresentation;
import com.certificadoranacional.ac.jpa.entity.Cliente;

@Component
public class ClienteJpaConverter extends AbstractConverter<Cliente, ClienteRepresentation> {

  @Autowired
  private UsuarioJpaConverter usuarioJpaConverter;

  public ClienteJpaConverter() {
    super();
  }

  @Override
  public ClienteRepresentation convert(final Cliente obj) {
    if (obj == null) {
      return null;
    }
    ClienteRepresentation rep = new ClienteRepresentation();
    rep.setDocumento(obj.getDocumento());
    rep.setEmail(obj.getEmail());
    rep.setId(obj.getId());
    rep.setNome(obj.getNome());
    rep.setUsuario(this.usuarioJpaConverter.convert(obj.getUsuario()));
    return rep;
  }

  @Override
  public Cliente convertBack(final ClienteRepresentation rep) {
    if (rep == null) {
      return null;
    }
    Cliente obj = new Cliente();
    obj.setDocumento(rep.getDocumento());
    obj.setEmail(rep.getEmail());
    obj.setId(rep.getId());
    obj.setNome(rep.getNome());
    obj.setUsuario(this.usuarioJpaConverter.convertBack(rep.getUsuario()));
    return obj;
  }

}
