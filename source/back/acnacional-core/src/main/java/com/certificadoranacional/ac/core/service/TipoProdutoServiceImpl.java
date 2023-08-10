package com.certificadoranacional.ac.core.service;

import com.certificadoranacional.ac.core.converter.TipoProdutoJpaConverter;
import com.certificadoranacional.ac.core.model.TipoProdutoRepresentation;
import com.certificadoranacional.ac.jpa.entity.TipoProduto;
import com.certificadoranacional.ac.jpa.repository.TipoProdutoJpaRepository;
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
public class TipoProdutoServiceImpl implements TipoProdutoService {

  @Autowired
  private TipoProdutoJpaRepository tipoProdutoJpaRepository;

  @Autowired
  private TipoProdutoJpaConverter tipoProdutoJpaConverter;

  public TipoProdutoServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public TipoProdutoRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    TipoProduto obj = this.tipoProdutoJpaRepository.findById(id).orElse(null);
    if (obj == null) {
      obj = this.tipoProdutoJpaRepository.findByCodigo(id);
    }
    return this.tipoProdutoJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<TipoProdutoRepresentation> listByActive(final Pageable pageable) {
    Pageable newPageable = SpringRepositoryHelper.toSort(pageable, "ordem", "nome");
    Page<TipoProduto> page = this.tipoProdutoJpaRepository.findByAtivo(Boolean.TRUE, newPageable);
    return this.tipoProdutoJpaConverter.convert(page);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<TipoProdutoRepresentation> list(final Pageable pageable) {
    Pageable newPageable = SpringRepositoryHelper.toSort(pageable, "ordem", "nome");
    Page<TipoProduto> page = this.tipoProdutoJpaRepository.findAll(newPageable);
    return this.tipoProdutoJpaConverter.convert(page);
  }

}
