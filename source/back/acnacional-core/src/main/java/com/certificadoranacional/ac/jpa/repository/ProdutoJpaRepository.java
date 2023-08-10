package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.certificadoranacional.ac.jpa.entity.Produto;

public interface ProdutoJpaRepository extends JpaRepository<Produto, String> {

  Produto findByCodigo(String codigo);

  @Query(
      value = "SELECT p FROM Produto AS p WHERE (:tipo = '' OR p.tipo.id = :tipo) AND (:grupo = '' OR p.grupo.id = :grupo) AND (:subgrupo = '' OR p.subGrupo.id = :subgrupo) AND (:ativo = '' OR p.ativo = true) AND p.nome LIKE :nome ORDER BY p.ordem, p.nome",
      countQuery = "SELECT COUNT(p) FROM Produto AS p WHERE (:tipo = '' OR p.tipo.id = :tipo) AND (:grupo = '' OR p.grupo.id = :grupo) AND (:subgrupo = '' OR p.subGrupo.id = :subgrupo) AND (:ativo = '' OR p.ativo = true) AND p.nome LIKE :nome")
  Page<Produto> findByNome(@Param("tipo") String tipo, @Param("grupo") String grupo, @Param("subgrupo") String subgrupo, @Param("nome") String nome, @Param("ativo") String ativo,
      Pageable pageable);

}
