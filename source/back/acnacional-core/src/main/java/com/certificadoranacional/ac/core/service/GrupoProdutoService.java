package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.GrupoProdutoRepresentation;

public interface GrupoProdutoService {

  GrupoProdutoRepresentation get(String id);

  GrupoProdutoRepresentation save(GrupoProdutoRepresentation rep);

  GrupoProdutoRepresentation update(GrupoProdutoRepresentation rep);

  Boolean delete(String id);

  Page<GrupoProdutoRepresentation> listByActive(String tipo, Pageable pageable);

  Page<GrupoProdutoRepresentation> list(String tipo, Pageable pageable);

}
