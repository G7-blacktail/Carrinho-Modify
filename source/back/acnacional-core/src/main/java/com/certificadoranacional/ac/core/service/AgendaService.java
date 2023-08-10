package com.certificadoranacional.ac.core.service;

import com.certificadoranacional.ac.core.model.AgendaRepresentation;

public interface AgendaService {

  AgendaRepresentation get(String idPosto, Integer mes, Integer ano);

}
