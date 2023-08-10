package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.certificadoranacional.ac.jpa.entity.Posto;

public interface PostoJpaRepository extends JpaRepository<Posto, String> {

  Posto findByCodigo(String codigo);

  @Query(
      value = "SELECT p FROM Posto AS p WHERE (:idUf = '' OR p.uf.id = :idUf) AND (:idMunicipio = '' OR p.municipio.id = :idMunicipio) AND (:status = 0 OR p.ativo = true) AND p.nome LIKE :nome ORDER BY p.nome",
      countQuery = "SELECT COUNT(p) FROM Posto AS p WHERE (:idUf = '' OR p.uf.id = :idUf) AND (:idMunicipio = '' OR p.municipio.id = :idMunicipio) AND (:status = 0 OR p.ativo = true) AND p.nome LIKE :nome")
  Page<Posto> list(@Param("idUf") String idUf, @Param("idMunicipio") String idMunicipio, @Param("status") Integer status, @Param("nome") String nome,
      Pageable pageable);

}
