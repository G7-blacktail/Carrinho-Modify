package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.MunicipioRepresentation;

public interface MunicipioService {

  MunicipioRepresentation get(String id);

  Page<MunicipioRepresentation> listByUf(String uf, Boolean status, String filter, Pageable pageable);

}
