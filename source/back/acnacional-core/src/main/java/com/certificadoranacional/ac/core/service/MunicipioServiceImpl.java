package com.certificadoranacional.ac.core.service;

import com.certificadoranacional.ac.core.converter.MunicipioJpaConverter;
import com.certificadoranacional.ac.core.model.MunicipioRepresentation;
import com.certificadoranacional.ac.jpa.entity.Municipio;
import com.certificadoranacional.ac.jpa.entity.UF;
import com.certificadoranacional.ac.jpa.repository.MunicipioJpaRepository;
import com.certificadoranacional.ac.jpa.repository.UfJpaRepository;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MunicipioServiceImpl implements MunicipioService {

  @Autowired
  private MunicipioJpaRepository municipioJpaRepository;

  @Autowired
  private MunicipioJpaConverter municipioJpaConverter;

  @Autowired
  private UfJpaRepository ufJpaRepository;

  public MunicipioServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public MunicipioRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Municipio obj = this.municipioJpaRepository.findById(id).orElse(null);
    return this.municipioJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<MunicipioRepresentation> listByUf(final String uf, Boolean status, final String filter, final Pageable pageable) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(uf), "UF não pode ser vazia");
    UF e = this.ufJpaRepository.findById(uf).orElse(null);
    Preconditions.checkState(e != null, "UF não encontrada");
    String str = SpringRepositoryHelper.getLikeValue(filter);

    if ((status != null) && (status.booleanValue())) {
      Page<Municipio> page = this.municipioJpaRepository.findByUfAndNomeAndActive(e.getCodigo(), str, pageable);
      return this.municipioJpaConverter.convert(page);
    }

    Page<Municipio> page = this.municipioJpaRepository.findByUfAndNome(e.getCodigo(), str, pageable);
    return this.municipioJpaConverter.convert(page);
  }

}
