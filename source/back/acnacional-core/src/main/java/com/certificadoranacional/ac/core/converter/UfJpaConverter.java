package com.certificadoranacional.ac.core.converter;

import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.UfRepresentation;
import com.certificadoranacional.ac.jpa.entity.UF;

@Component
public class UfJpaConverter extends AuxJpaConverter<UF, UfRepresentation> {

  public UfJpaConverter() {
    super();
  }

}
