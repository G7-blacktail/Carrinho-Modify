package com.certificadoranacional.ac.core.service;

import com.certificadoranacional.ac.core.model.UsuarioRepresentation;

public interface SessionService {

  boolean isLogado();

  boolean isAdministrador();

  boolean isGerente();

  boolean isAgente();

  UsuarioRepresentation getUsuario();

  String getIdUsuario();

}
