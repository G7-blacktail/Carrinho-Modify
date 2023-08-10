package com.certificadoranacional.ac.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.converter.GrupoProdutoJpaConverter;
import com.certificadoranacional.ac.core.model.GrupoProdutoRepresentation;
import com.certificadoranacional.ac.jpa.entity.GrupoProduto;
import com.certificadoranacional.ac.jpa.repository.GrupoProdutoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.TipoProdutoJpaRepository;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

@Service
public class GrupoProdutoServiceImpl implements GrupoProdutoService {

  @Autowired
  private GrupoProdutoJpaRepository grupoProdutoJpaRepository;

  @Autowired
  private TipoProdutoJpaRepository tipoProdutoJpaRepository;

  @Autowired
  private GrupoProdutoJpaConverter grupoProdutoJpaConverter;

  public GrupoProdutoServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public GrupoProdutoRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    GrupoProduto obj = this.grupoProdutoJpaRepository.findById(id).orElse(null);
    if (obj == null) {
      obj = this.grupoProdutoJpaRepository.findByCodigo(id);
    }
    return this.grupoProdutoJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public GrupoProdutoRepresentation save(final GrupoProdutoRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Grupo não pode ser nulo");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getCodigo()), "Código não pode ser vazio");
    Preconditions.checkArgument(rep.getOrdem() != null, "Ordem não pode ser vazia");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getNome()), "Nome não pode ser vazio");
    Preconditions.checkArgument(rep.getTipo() != null && rep.getTipo().getId() != null, "Tipo não pode ser nulo");

    GrupoProduto tmp = this.grupoProdutoJpaRepository.findByCodigo(rep.getCodigo());
    Preconditions.checkState(tmp == null, "Já existe um grupo com esse código");

    GrupoProduto entity = this.grupoProdutoJpaConverter.convertBack(rep);
    entity.setAtivo(Boolean.TRUE);
    entity.setTipo(this.tipoProdutoJpaRepository.getById(rep.getTipo().getId()));
    entity = this.grupoProdutoJpaRepository.save(entity);
    return this.grupoProdutoJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public GrupoProdutoRepresentation update(final GrupoProdutoRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Grupo não pode ser nulo");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getId()), "ID não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getCodigo()), "Código não pode ser vazio");
    Preconditions.checkArgument(rep.getOrdem() != null, "Ordem não pode ser vazia");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getNome()), "Nome não pode ser vazio");
    Preconditions.checkArgument(rep.getTipo() != null && rep.getTipo().getId() != null, "Tipo não pode ser nulo");

    GrupoProduto tmp = this.grupoProdutoJpaConverter.convertBack(rep);
    GrupoProduto entity = this.grupoProdutoJpaRepository.findById(rep.getId()).orElse(null);
    Preconditions.checkState(entity != null, "Produto não encontrado");

    entity.setTipo(this.tipoProdutoJpaRepository.getById(rep.getTipo().getId()));

    entity.setAtivo(tmp.getAtivo());
    entity.setDescricao(tmp.getDescricao());
    entity.setImagem(tmp.getImagem());
    entity.setNome(tmp.getNome());
    entity.setOrdem(tmp.getOrdem());

    entity = this.grupoProdutoJpaRepository.save(entity);
    return this.grupoProdutoJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Boolean delete(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    GrupoProduto obj = this.grupoProdutoJpaRepository.findById(id).orElse(null);
    if (obj != null) {
      this.grupoProdutoJpaRepository.delete(obj);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<GrupoProdutoRepresentation> listByActive(final String tipo, final Pageable pageable) {
    Pageable newPageable = SpringRepositoryHelper.toSort(pageable, "ordem", "nome");
    Page<GrupoProduto> page = this.grupoProdutoJpaRepository.findByTipoIdAndAtivo(tipo, Boolean.TRUE, newPageable);
    return this.grupoProdutoJpaConverter.convert(page);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<GrupoProdutoRepresentation> list(final String tipo, final Pageable pageable) {
    Pageable newPageable = SpringRepositoryHelper.toSort(pageable, "ordem", "nome");
    Page<GrupoProduto> page = this.grupoProdutoJpaRepository.findByTipoId(tipo, newPageable);
    return this.grupoProdutoJpaConverter.convert(page);
  }

}
