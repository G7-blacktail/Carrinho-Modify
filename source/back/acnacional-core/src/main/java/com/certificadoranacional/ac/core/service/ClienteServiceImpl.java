package com.certificadoranacional.ac.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.converter.ClienteJpaConverter;
import com.certificadoranacional.ac.core.model.ClienteRepresentation;
import com.certificadoranacional.ac.jpa.entity.Cliente;
import com.certificadoranacional.ac.jpa.entity.Usuario;
import com.certificadoranacional.ac.jpa.repository.ClienteJpaRepository;
import com.certificadoranacional.ac.jpa.repository.UsuarioJpaRepository;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

@Service
public class ClienteServiceImpl implements ClienteService {

  @Autowired
  private ClienteJpaRepository clienteJpaRepository;

  @Autowired
  private UsuarioJpaRepository usuarioJpaRepository;

  @Autowired
  private ClienteJpaConverter clienteJpaConverter;

  public ClienteServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ClienteRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Cliente obj = this.clienteJpaRepository.findById(id).orElse(null);
    return this.clienteJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Cliente getClienteByIdUsuario(final String id) {
    Usuario usuario = this.usuarioJpaRepository.findById(id).orElse(null);
    Cliente obj = this.clienteJpaRepository.findByUsuarioId(id);
    if ((obj == null) && (usuario != null)) {
      obj = new Cliente();
      obj.setEmail(usuario.getEmail());
      obj.setNome(usuario.getNome());
      obj.setUsuario(usuario);
      obj = this.clienteJpaRepository.saveAndFlush(obj);
    } else if (usuario != null) {
      obj.setNome(usuario.getNome());
      obj = this.clienteJpaRepository.saveAndFlush(obj);
    }
    return obj;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<ClienteRepresentation> list(final String filter, final Pageable pageable) {
    String str = MoreObjects.firstNonNull(filter, "");
    Page<Cliente> page = this.clienteJpaRepository.findByNomeContaining(str, pageable);
    return this.clienteJpaConverter.convert(page);
  }

}
