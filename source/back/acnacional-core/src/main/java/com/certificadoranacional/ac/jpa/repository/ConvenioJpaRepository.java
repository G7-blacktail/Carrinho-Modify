package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.certificadoranacional.ac.jpa.entity.Convenio;

public interface ConvenioJpaRepository extends JpaRepository<Convenio, String> {

  Convenio findByCodigo(String codigo);

  Page<Convenio> findByNomeContaining(String nome, Pageable pageable);

}
