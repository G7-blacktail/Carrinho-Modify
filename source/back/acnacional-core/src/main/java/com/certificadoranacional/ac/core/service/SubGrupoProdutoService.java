package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.SubGrupoProdutoRepresentation;

public interface SubGrupoProdutoService {

  SubGrupoProdutoRepresentation get(String id);

  SubGrupoProdutoRepresentation save(SubGrupoProdutoRepresentation rep);

  SubGrupoProdutoRepresentation update(SubGrupoProdutoRepresentation rep);

  Boolean delete(String id);

  Page<SubGrupoProdutoRepresentation> listByActive(String grupo, Pageable pageable);

  Page<SubGrupoProdutoRepresentation> list(String grupo, Pageable pageable);

}
