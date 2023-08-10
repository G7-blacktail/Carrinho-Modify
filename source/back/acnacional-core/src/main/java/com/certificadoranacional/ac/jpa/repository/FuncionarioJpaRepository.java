package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.certificadoranacional.ac.jpa.entity.Funcionario;

public interface FuncionarioJpaRepository extends JpaRepository<Funcionario, String> {

  Page<Funcionario> findByUsuarioId(String idUsuario, Pageable pageable);

  Page<Funcionario> findByPostoIdAndNomeContaining(String idPosto, String nome, Pageable pageable);

}
