package com.certificadoranacional.ac.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.converter.FuncionarioJpaConverter;
import com.certificadoranacional.ac.core.model.FuncionarioRepresentation;
import com.certificadoranacional.ac.jpa.entity.Funcionario;
import com.certificadoranacional.ac.jpa.repository.FuncionarioJpaRepository;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

  @Autowired
  private FuncionarioJpaRepository funcionarioJpaRepository;

  @Autowired
  private FuncionarioJpaConverter funcionarioJpaConverter;

  public FuncionarioServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public FuncionarioRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Funcionario obj = this.funcionarioJpaRepository.findById(id).orElse(null);
    return this.funcionarioJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public FuncionarioRepresentation save(final FuncionarioRepresentation rep) {
    /*Preconditions.checkArgument(rep != null, "Funcionario não pode ser nulo");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getCodigo()), "Código não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getNome()), "Nome não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getResponsavel()), "Responsável não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getEndereco()), "Endereço não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getCidade()), "Cidade não pode ser vazio");
    Preconditions.checkArgument(rep.getUf() != null, "UF não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getEmail()), "E-mail não pode ser vazio");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getTelefone()), "Telefone não pode ser vazio");

    Funcionario tmp = this.funcionarioJpaRepository.findByCodigo(rep.getCodigo());
    Preconditions.checkState(tmp == null, "Já existe um funcionario com esse código");

    Funcionario entity = this.funcionarioJpaConverter.convertBack(rep);
    this.funcionarioJpaRepository.save(entity);
    return this.funcionarioJpaConverter.convert(entity);*/
    return null;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Boolean delete(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Funcionario obj = this.funcionarioJpaRepository.findById(id).orElse(null);
    if (obj != null) {
      this.funcionarioJpaRepository.delete(obj);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<FuncionarioRepresentation> listByIdPosto(final String idPosto, final String filter, final Pageable pageable) {
    String str = MoreObjects.firstNonNull(filter, "");
    Page<Funcionario> page = this.funcionarioJpaRepository.findByPostoIdAndNomeContaining(idPosto, str, pageable);
    return this.funcionarioJpaConverter.convert(page);
  }

}
