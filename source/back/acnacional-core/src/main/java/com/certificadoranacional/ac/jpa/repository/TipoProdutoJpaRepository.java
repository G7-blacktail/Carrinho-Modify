package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.certificadoranacional.ac.jpa.entity.TipoProduto;

public interface TipoProdutoJpaRepository extends JpaRepository<TipoProduto, String> {

  TipoProduto findByCodigo(String codigo);

  Page<TipoProduto> findByAtivo(Boolean ativo, Pageable pageable);

}
