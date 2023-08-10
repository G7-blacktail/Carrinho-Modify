package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.PostoRepresentation;

public interface PostoService {

  PostoRepresentation get(String id);

  PostoRepresentation save(PostoRepresentation rep);

  PostoRepresentation update(PostoRepresentation rep);

  Boolean delete(String id);

  Page<PostoRepresentation> listByUfMunicipio(String idUf, String idMunicipio, Boolean status, String filter, Pageable pageable);

}
