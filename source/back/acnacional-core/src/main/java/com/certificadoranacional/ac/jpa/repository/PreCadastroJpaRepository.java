package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.certificadoranacional.ac.jpa.entity.PreCadastro;

public interface PreCadastroJpaRepository extends JpaRepository<PreCadastro, String> {

  Page<PreCadastro> findByClienteId(String idCliente, Pageable pageable);
  
  Page<PreCadastro> findByClienteIdAndTipo(String idCliente, Integer tipo, Pageable pageable);

}
