package com.certificadoranacional.ac.core.service;

import com.certificadoranacional.ac.core.converter.UfJpaConverter;
import com.certificadoranacional.ac.core.model.UfRepresentation;
import com.certificadoranacional.ac.jpa.entity.UF;
import com.certificadoranacional.ac.jpa.repository.UfJpaRepository;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UfServiceImpl implements UfService {

  @Autowired
  private UfJpaRepository ufJpaRepository;

  @Autowired
  private UfJpaConverter ufJpaConverter;

  public UfServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public UfRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    UF obj = this.ufJpaRepository.findById(id).orElse(null);
    return this.ufJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<UfRepresentation> list(final Boolean status, final Pageable pageable) {
    if ((status != null) && (status.booleanValue())) {
      Page<UF> page = this.ufJpaRepository.findByActive(pageable);
      return this.ufJpaConverter.convert(page);
    }
    Page<UF> page = this.ufJpaRepository.findAll(pageable);
    return this.ufJpaConverter.convert(page);
  }

}
