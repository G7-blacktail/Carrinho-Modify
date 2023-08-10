package com.certificadoranacional.ac.core.service;

import com.certificadoranacional.ac.core.converter.ProdutoJpaConverter;
import com.certificadoranacional.ac.core.model.ProdutoRepresentation;
import com.certificadoranacional.ac.jpa.entity.Produto;
import com.certificadoranacional.ac.jpa.repository.GrupoProdutoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.ProdutoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.SubGrupoProdutoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.TipoProdutoJpaRepository;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.io.ByteSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

  @Autowired
  private ProdutoJpaRepository produtoJpaRepository;

  @Autowired
  private ProdutoJpaConverter produtoJpaConverter;

  @Autowired
  private TipoProdutoJpaRepository tipoProdutoJpaRepository;

  @Autowired
  private GrupoProdutoJpaRepository grupoProdutoJpaRepository;

  @Autowired
  private SubGrupoProdutoJpaRepository subGrupoProdutoJpaRepository;

  public ProdutoServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ProdutoRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Produto obj = this.produtoJpaRepository.findById(id).orElse(null);
    if (obj == null) {
      obj = this.produtoJpaRepository.findByCodigo(id);
    }
    return this.produtoJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ProdutoRepresentation save(final ProdutoRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Produto não pode ser nulo");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getCodigo()), "Código não pode ser vazio");
    Preconditions.checkArgument(rep.getOrdem() != null, "Ordem não pode ser vazia");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getNome()), "Nome não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getDescricao()), "Descrição não pode ser vazia");
    Preconditions.checkArgument(rep.getTipo() != null && rep.getTipo().getId() != null, "Tipo não pode ser nulo");
    Preconditions.checkArgument(rep.getGrupo() != null && rep.getGrupo().getId() != null, "Grupo não pode ser nulo");
    Preconditions.checkArgument(rep.getSubGrupo() != null && rep.getSubGrupo().getId() != null, "SubGrupo não pode ser nulo");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getValor()), "Valor não pode ser vazio");

    Produto tmp = this.produtoJpaRepository.findByCodigo(rep.getCodigo());
    Preconditions.checkState(tmp == null, "Já existe um produto com esse código");

    Produto entity = this.produtoJpaConverter.convertBack(rep);
    entity.setGrupo(this.grupoProdutoJpaRepository.getById(rep.getGrupo().getId()));
    entity.setSubGrupo(this.subGrupoProdutoJpaRepository.getById(rep.getSubGrupo().getId()));
    entity.setTipo(this.tipoProdutoJpaRepository.getById(rep.getTipo().getId()));
    entity = this.produtoJpaRepository.save(entity);
    return this.produtoJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ProdutoRepresentation update(final ProdutoRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Produto não pode ser nulo");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getId()), "ID não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getCodigo()), "Código não pode ser vazio");
    Preconditions.checkArgument(rep.getOrdem() != null, "Ordem não pode ser vazia");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getNome()), "Nome não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getDescricao()), "Descrição não pode ser vazia");
    Preconditions.checkArgument(rep.getTipo() != null && rep.getTipo().getId() != null, "Tipo não pode ser nulo");
    Preconditions.checkArgument(rep.getGrupo() != null && rep.getGrupo().getId() != null, "Grupo não pode ser nulo");
    Preconditions.checkArgument(rep.getSubGrupo() != null && rep.getSubGrupo().getId() != null, "SubGrupo não pode ser nulo");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getValor()), "Valor não pode ser vazio");

    Produto tmp = this.produtoJpaConverter.convertBack(rep);
    Produto entity = this.produtoJpaRepository.findById(rep.getId()).orElse(null);
    Preconditions.checkState(entity != null, "Produto não encontrado");

    entity.setGrupo(this.grupoProdutoJpaRepository.getById(rep.getGrupo().getId()));
    entity.setSubGrupo(this.subGrupoProdutoJpaRepository.getById(rep.getSubGrupo().getId()));
    entity.setTipo(this.tipoProdutoJpaRepository.getById(rep.getTipo().getId()));

    entity.setAtivo(tmp.getAtivo());
    entity.setCodigoSerpro(tmp.getCodigoSerpro());
    entity.setDescricao(tmp.getDescricao());
    entity.setImagem(tmp.getImagem());
    entity.setNome(tmp.getNome());
    entity.setOrdem(tmp.getOrdem());
    entity.setValidade(tmp.getValidade());
    entity.setValor(tmp.getValor());
    entity.setValorDesconto(tmp.getValorDesconto());
    entity.setValorReferencia(tmp.getValorReferencia());

    entity = this.produtoJpaRepository.save(entity);
    return this.produtoJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Boolean delete(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Produto obj = this.produtoJpaRepository.findById(id).orElse(null);
    if (obj != null) {
      this.produtoJpaRepository.delete(obj);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Boolean load(final ByteSource xlsx) {
    throw new IllegalStateException();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<ProdutoRepresentation> list(String tipo, String grupo, String subGrupo, String nome, final Boolean ativo, final Pageable pageable) {
    String str1 = MoreObjects.firstNonNull(tipo, "");
    String str2 = MoreObjects.firstNonNull(grupo, "");
    String str3 = MoreObjects.firstNonNull(subGrupo, "");
    String str4 = SpringRepositoryHelper.getLikeValue(nome);
    String str5 = (ativo != null && ativo.booleanValue()) ? "S" : "";
    Page<Produto> page = this.produtoJpaRepository.findByNome(str1, str2, str3, str4, str5, pageable);
    return this.produtoJpaConverter.convert(page);
  }

}
