package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.certificadoranacional.ac.jpa.entity.GrupoProduto;

public interface GrupoProdutoJpaRepository extends JpaRepository<GrupoProduto, String> {

  GrupoProduto findByCodigo(String codigo);

  GrupoProduto findByTipoIdAndCodigo(String tipo, String codigo);

  Page<GrupoProduto> findByTipoId(String tipo, Pageable pageable);

  Page<GrupoProduto> findByTipoIdAndAtivo(String tipo, Boolean ativo, Pageable pageable);

}
