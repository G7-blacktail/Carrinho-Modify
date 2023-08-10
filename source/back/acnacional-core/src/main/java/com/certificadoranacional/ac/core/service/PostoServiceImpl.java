package com.certificadoranacional.ac.core.service;

import com.certificadoranacional.ac.core.converter.PostoJpaConverter;
import com.certificadoranacional.ac.core.model.PostoRepresentation;
import com.certificadoranacional.ac.jpa.entity.Municipio;
import com.certificadoranacional.ac.jpa.entity.Posto;
import com.certificadoranacional.ac.jpa.repository.MunicipioJpaRepository;
import com.certificadoranacional.ac.jpa.repository.PostoJpaRepository;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostoServiceImpl implements PostoService {

  @Autowired
  private PostoJpaRepository postoJpaRepository;

  @Autowired
  private PostoJpaConverter postoJpaConverter;

  @Autowired
  private MunicipioJpaRepository municipioJpaRepository;

  public PostoServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PostoRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Posto obj = this.postoJpaRepository.findById(id).orElse(null);
    return this.postoJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PostoRepresentation save(final PostoRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Posto não pode ser nulo");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getCodigo()), "Código não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getNome()), "Nome não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getResponsavel()), "Responsável não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getEndereco()), "Endereço não pode ser vazio");
    Preconditions.checkArgument(rep.getMunicipio() != null, "Município não pode ser vazio");
    Preconditions.checkArgument(rep.getUf() != null, "UF não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getEmail()), "E-mail não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getTelefone()), "Telefone não pode ser vazio");

    Posto tmp = this.postoJpaRepository.findByCodigo(rep.getCodigo());
    Preconditions.checkState(tmp == null, "Já existe um posto com esse código");

    Municipio m = null;
    if (!Strings.isNullOrEmpty(rep.getMunicipio().getId())) {
      m = this.municipioJpaRepository.findById(rep.getMunicipio().getId()).orElse(null);
    } else if (!Strings.isNullOrEmpty(rep.getMunicipio().getCodigo())) {
      m = this.municipioJpaRepository.findByCodigo(rep.getMunicipio().getCodigo());
    }
    Preconditions.checkState(m != null, "Município não encontrado");

    Posto entity = this.postoJpaConverter.convertBack(rep);
    entity.setMunicipio(m);
    entity = this.postoJpaRepository.save(entity);
    return this.postoJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PostoRepresentation update(final PostoRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Posto não pode ser nulo");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getId()), "ID não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getCodigo()), "Código não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getNome()), "Nome não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getResponsavel()), "Responsável não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getEndereco()), "Endereço não pode ser vazio");
    Preconditions.checkArgument(rep.getMunicipio() != null, "Município não pode ser vazio");
    Preconditions.checkArgument(rep.getUf() != null, "UF não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getEmail()), "E-mail não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getTelefone()), "Telefone não pode ser vazio");

    Municipio m = null;
    if (!Strings.isNullOrEmpty(rep.getMunicipio().getId())) {
      m = this.municipioJpaRepository.findById(rep.getMunicipio().getId()).orElse(null);
    } else if (!Strings.isNullOrEmpty(rep.getMunicipio().getCodigo())) {
      m = this.municipioJpaRepository.findByCodigo(rep.getMunicipio().getCodigo());
    }
    Preconditions.checkState(m != null, "Município não encontrado");

    Posto tmp = this.postoJpaConverter.convertBack(rep);
    Posto entity = this.postoJpaRepository.findById(rep.getId()).orElse(null);
    Preconditions.checkState(entity != null, "Posto não encontrado");
    entity.setAtivo(tmp.getAtivo());
    entity.setBairro(tmp.getBairro());
    entity.setCep(tmp.getCep());
    entity.setComplemento(tmp.getComplemento());
    entity.setEmail(tmp.getEmail());
    entity.setEndereco(tmp.getEndereco());
    entity.setMapa(tmp.getMapa());
    entity.setMunicipio(m);
    entity.setNome(tmp.getNome());
    entity.setNumero(tmp.getNumero());
    entity.setResponsavel(tmp.getResponsavel());
    entity.setTelefone(tmp.getTelefone());
    entity.setUf(tmp.getUf());

    entity = this.postoJpaRepository.save(entity);
    return this.postoJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Boolean delete(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Posto obj = this.postoJpaRepository.findById(id).orElse(null);
    if (obj != null) {
      this.postoJpaRepository.delete(obj);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<PostoRepresentation> listByUfMunicipio(final String idUf, final String idMunicipio, final Boolean status, final String filter,
      final Pageable pageable) {
    String u = MoreObjects.firstNonNull(idUf, "");
    String m = MoreObjects.firstNonNull(idMunicipio, "");
    String str = SpringRepositoryHelper.getLikeValue(filter);
    Integer i = Integer.valueOf(0);
    if ((status != null) && (status.booleanValue())) {
      i = Integer.valueOf(1);
    }
    Page<Posto> page = this.postoJpaRepository.list(u, m, i, str, pageable);
    return this.postoJpaConverter.convert(page);
  }

}
