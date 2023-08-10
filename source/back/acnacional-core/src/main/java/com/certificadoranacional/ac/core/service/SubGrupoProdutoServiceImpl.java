package com.certificadoranacional.ac.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.converter.SubGrupoProdutoJpaConverter;
import com.certificadoranacional.ac.core.model.SubGrupoProdutoRepresentation;
import com.certificadoranacional.ac.jpa.entity.GrupoProduto;
import com.certificadoranacional.ac.jpa.entity.SubGrupoProduto;
import com.certificadoranacional.ac.jpa.repository.GrupoProdutoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.SubGrupoProdutoJpaRepository;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

@Service
public class SubGrupoProdutoServiceImpl implements SubGrupoProdutoService {

  @Autowired
  private SubGrupoProdutoJpaRepository subGrupoProdutoJpaRepository;

  @Autowired
  private GrupoProdutoJpaRepository grupoProdutoJpaRepository;

  @Autowired
  private SubGrupoProdutoJpaConverter subGrupoProdutoJpaConverter;

  public SubGrupoProdutoServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public SubGrupoProdutoRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    SubGrupoProduto obj = this.subGrupoProdutoJpaRepository.findById(id).orElse(null);
    return this.subGrupoProdutoJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public SubGrupoProdutoRepresentation save(final SubGrupoProdutoRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Subgrupo não pode ser nulo");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getCodigo()), "Código não pode ser vazio");
    Preconditions.checkArgument(rep.getOrdem() != null, "Ordem não pode ser vazia");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getNome()), "Nome não pode ser vazio");
    Preconditions.checkArgument(rep.getGrupo() != null && rep.getGrupo().getId() != null, "Grupo não pode ser nulo");

    SubGrupoProduto tmp = this.subGrupoProdutoJpaRepository.findByGrupoIdAndCodigo(rep.getGrupo().getId(), rep.getCodigo());
    Preconditions.checkState(tmp == null, "Já existe um subgrupo com esse código");

    SubGrupoProduto entity = this.subGrupoProdutoJpaConverter.convertBack(rep);
    entity.setAtivo(Boolean.TRUE);
    entity.setGrupo(this.grupoProdutoJpaRepository.getById(rep.getGrupo().getId()));
    entity = this.subGrupoProdutoJpaRepository.save(entity);
    return this.subGrupoProdutoJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public SubGrupoProdutoRepresentation update(final SubGrupoProdutoRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Produto não pode ser nulo");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getId()), "ID não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getCodigo()), "Código não pode ser vazio");
    Preconditions.checkArgument(rep.getOrdem() != null, "Ordem não pode ser vazia");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getNome()), "Nome não pode ser vazio");
    Preconditions.checkArgument(rep.getGrupo() != null && rep.getGrupo().getId() != null, "Grupo não pode ser nulo");

    SubGrupoProduto tmp = this.subGrupoProdutoJpaConverter.convertBack(rep);
    SubGrupoProduto entity = this.subGrupoProdutoJpaRepository.findById(rep.getId()).orElse(null);
    Preconditions.checkState(entity != null, "Subgrupo não encontrado");

    entity.setGrupo(this.grupoProdutoJpaRepository.getById(rep.getGrupo().getId()));

    entity.setAtivo(tmp.getAtivo());
    entity.setDescricao(tmp.getDescricao());
    entity.setImagem(tmp.getImagem());
    entity.setNome(tmp.getNome());
    entity.setOrdem(tmp.getOrdem());

    entity = this.subGrupoProdutoJpaRepository.save(entity);
    return this.subGrupoProdutoJpaConverter.convert(entity);
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
  public Page<SubGrupoProdutoRepresentation> listByActive(final String grupo, final Pageable pageable) {
    Pageable newPageable = SpringRepositoryHelper.toSort(pageable, "ordem", "nome");
    Page<SubGrupoProduto> page = this.subGrupoProdutoJpaRepository.findByGrupoIdAndAtivo(grupo, Boolean.TRUE, newPageable);
    return this.subGrupoProdutoJpaConverter.convert(page);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<SubGrupoProdutoRepresentation> list(final String grupo, final Pageable pageable) {
    Pageable newPageable = SpringRepositoryHelper.toSort(pageable, "ordem", "nome");
    Page<SubGrupoProduto> page = this.subGrupoProdutoJpaRepository.findByGrupoId(grupo, newPageable);
    return this.subGrupoProdutoJpaConverter.convert(page);
  }

}
