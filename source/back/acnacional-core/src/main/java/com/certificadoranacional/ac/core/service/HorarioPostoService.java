package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.HorarioPostoRepresentation;

public interface HorarioPostoService {

  HorarioPostoRepresentation get(String id);

  HorarioPostoRepresentation save(HorarioPostoRepresentation rep);

  Boolean delete(String id);

  Page<HorarioPostoRepresentation> listByIdPostoData(String idPosto, String data, Boolean status, Pageable pageable);

}
