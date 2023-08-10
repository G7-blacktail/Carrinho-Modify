package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.TipoProdutoRepresentation;

public interface TipoProdutoService {

  TipoProdutoRepresentation get(String id);

  Page<TipoProdutoRepresentation> listByActive(Pageable pageable);
  
  Page<TipoProdutoRepresentation> list(Pageable pageable);

}
