package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.certificadoranacional.ac.jpa.entity.Municipio;

public interface MunicipioJpaRepository extends JpaRepository<Municipio, String> {

  Municipio findByCodigo(String codigo);

  @Query(value = "SELECT m FROM Municipio AS m WHERE m.uf.codigo = :uf AND m.nome LIKE :nome ORDER BY m.nome",
      countQuery = "SELECT COUNT(m) FROM Municipio AS m WHERE m.uf.codigo = :uf AND m.nome LIKE :nome")
  Page<Municipio> findByUfAndNome(@Param("uf") String uf, @Param("nome") String nome, Pageable pageable);

  @Query(value = "SELECT m FROM Municipio AS m WHERE m.uf.codigo = :uf AND m.nome LIKE :nome AND m.postoList IS NOT EMPTY ORDER BY m.nome",
      countQuery = "SELECT COUNT(m) FROM Municipio AS m WHERE m.uf.codigo = :uf AND m.nome LIKE :nome AND m.postoList IS NOT EMPTY")
  Page<Municipio> findByUfAndNomeAndActive(@Param("uf") String uf, @Param("nome") String nome, Pageable pageable);

}
