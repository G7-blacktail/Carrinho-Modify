package com.certificadoranacional.ac.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.converter.ConvenioJpaConverter;
import com.certificadoranacional.ac.core.model.ConvenioRepresentation;
import com.certificadoranacional.ac.jpa.entity.Convenio;
import com.certificadoranacional.ac.jpa.repository.ConvenioJpaRepository;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

@Service
public class ConvenioServiceImpl implements ConvenioService {

  @Autowired
  private ConvenioJpaRepository convenioJpaRepository;

  @Autowired
  private ConvenioJpaConverter convenioJpaConverter;

  public ConvenioServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ConvenioRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Convenio obj = this.convenioJpaRepository.findById(id).orElse(null);
    if (obj == null) {
      obj = this.convenioJpaRepository.findByCodigo(id);
    }
    return this.convenioJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ConvenioRepresentation save(final ConvenioRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Convênio não pode ser nulo");
    Preconditions.checkArgument(Strings.isNullOrEmpty(rep.getId()), "ID deve ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getCodigo()), "Código pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getNome()), "Nome não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getValor()), "Valor não pode ser vazio");

    Convenio entity = this.convenioJpaConverter.convertBack(rep);
    entity.setAtivo(Boolean.TRUE);
    entity = this.convenioJpaRepository.save(entity);

    return this.convenioJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ConvenioRepresentation update(final ConvenioRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Convênio não pode ser nulo");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getId()), "ID não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getCodigo()), "Código pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getNome()), "Nome não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getValor()), "Valor não pode ser vazio");

    Convenio tmp = this.convenioJpaConverter.convertBack(rep);
    Convenio entity = this.convenioJpaRepository.findById(rep.getId()).orElse(null);
    Preconditions.checkState(entity != null, "Convênio não encontrado");

    entity.setAtivo(tmp.getAtivo());
    entity.setCodigo(tmp.getCodigo());
    entity.setNome(tmp.getNome());
    entity.setValor(tmp.getValor());

    entity = this.convenioJpaRepository.save(entity);
    return this.convenioJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<ConvenioRepresentation> list(final String nome, final Pageable pageable) {
    Page<Convenio> page = this.convenioJpaRepository.findByNomeContaining(nome, SpringRepositoryHelper.toSort(pageable, "nome"));
    return this.convenioJpaConverter.convert(page);
  }

}
