package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.PedidoRepresentation;

public interface PedidoService {

  PedidoRepresentation get(String id);

  Boolean delete(String id);

  Page<PedidoRepresentation> list(String filter, Boolean all, Pageable pageable);

}
