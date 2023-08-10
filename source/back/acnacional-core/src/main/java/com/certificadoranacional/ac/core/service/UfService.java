package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.UfRepresentation;

public interface UfService {

  UfRepresentation get(String id);

  Page<UfRepresentation> list(Boolean status, Pageable pageable);

}
