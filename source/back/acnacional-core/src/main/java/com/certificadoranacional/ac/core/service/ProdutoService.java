package com.certificadoranacional.ac.core.service;

import com.certificadoranacional.ac.core.model.ProdutoRepresentation;
import com.google.common.io.ByteSource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoService {

  ProdutoRepresentation get(String id);

  ProdutoRepresentation save(ProdutoRepresentation rep);

  ProdutoRepresentation update(ProdutoRepresentation rep);

  Boolean delete(String id);

  Boolean load(ByteSource xlsx);

  Page<ProdutoRepresentation> list(String tipo, String grupo, String subGrupo, String nome, Boolean ativo, Pageable pageable);

}
