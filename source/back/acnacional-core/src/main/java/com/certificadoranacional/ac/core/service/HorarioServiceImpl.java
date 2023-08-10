package com.certificadoranacional.ac.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.converter.HorarioJpaConverter;
import com.certificadoranacional.ac.core.model.HorarioRepresentation;
import com.certificadoranacional.ac.jpa.entity.Horario;
import com.certificadoranacional.ac.jpa.repository.HorarioJpaRepository;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

@Service
public class HorarioServiceImpl implements HorarioService {

  @Autowired
  private HorarioJpaRepository horarioJpaRepository;

  @Autowired
  private HorarioJpaConverter horarioJpaConverter;

  public HorarioServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public HorarioRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Horario obj = this.horarioJpaRepository.findById(id).orElse(null);
    return this.horarioJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<HorarioRepresentation> list(final Pageable pageable) {
    Page<Horario> page = this.horarioJpaRepository.findAll(pageable);
    return this.horarioJpaConverter.convert(page);
  }

}
