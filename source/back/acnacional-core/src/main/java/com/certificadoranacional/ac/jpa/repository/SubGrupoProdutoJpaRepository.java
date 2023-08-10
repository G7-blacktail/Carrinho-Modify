package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.certificadoranacional.ac.jpa.entity.SubGrupoProduto;

public interface SubGrupoProdutoJpaRepository extends JpaRepository<SubGrupoProduto, String> {

  SubGrupoProduto findByGrupoIdAndCodigo(String grupo, String codigo);

  Page<SubGrupoProduto> findByGrupoId(String grupo, Pageable pageable);

  Page<SubGrupoProduto> findByGrupoIdAndAtivo(String grupo, Boolean ativo, Pageable pageable);

}
