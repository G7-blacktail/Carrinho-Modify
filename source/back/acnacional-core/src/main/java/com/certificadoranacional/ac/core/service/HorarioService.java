package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.HorarioRepresentation;

public interface HorarioService {

  HorarioRepresentation get(String id);

  Page<HorarioRepresentation> list(Pageable pageable);

}
