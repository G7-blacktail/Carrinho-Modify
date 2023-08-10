package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.UsuarioRepresentation;

public interface UsuarioService {

  UsuarioRepresentation get(String id);

  UsuarioRepresentation save(UsuarioRepresentation rep);

  UsuarioRepresentation update(UsuarioRepresentation rep);

  void delete(String id);

  Page<UsuarioRepresentation> list(String nome, Pageable pageable);

}
