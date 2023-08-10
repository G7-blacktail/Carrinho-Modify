package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.ClienteRepresentation;
import com.certificadoranacional.ac.jpa.entity.Cliente;

public interface ClienteService {

  ClienteRepresentation get(String id);

  Cliente getClienteByIdUsuario(String id);

  Page<ClienteRepresentation> list(String filter, Pageable pageable);

}
