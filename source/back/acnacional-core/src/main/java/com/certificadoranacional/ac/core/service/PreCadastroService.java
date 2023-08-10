package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.PreCadastroRepresentation;

public interface PreCadastroService {

  PreCadastroRepresentation get(String id);

  PreCadastroRepresentation save(PreCadastroRepresentation rep);

  PreCadastroRepresentation update(PreCadastroRepresentation rep);

  Boolean delete(String id);

  Page<PreCadastroRepresentation> listByCliente(String idCliente, Integer tipo, Pageable pageable);

}
