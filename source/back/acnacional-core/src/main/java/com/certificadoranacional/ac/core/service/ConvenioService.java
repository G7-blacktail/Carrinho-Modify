package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.ConvenioRepresentation;

public interface ConvenioService {

  ConvenioRepresentation get(String id);

  ConvenioRepresentation save(ConvenioRepresentation rep);

  ConvenioRepresentation update(ConvenioRepresentation rep);

  Page<ConvenioRepresentation> list(String nome, Pageable pageable);

}
