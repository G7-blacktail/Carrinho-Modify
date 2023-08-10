package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.FuncionarioRepresentation;

public interface FuncionarioService {

  FuncionarioRepresentation get(String id);

  FuncionarioRepresentation save(FuncionarioRepresentation rep);

  Boolean delete(String id);

  Page<FuncionarioRepresentation> listByIdPosto(String idPosto, String filter, Pageable pageable);

}
