package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.certificadoranacional.ac.jpa.entity.UF;

public interface UfJpaRepository extends JpaRepository<UF, String> {

  @Query(value = "SELECT u FROM UF AS u WHERE u.postoList IS NOT EMPTY ORDER BY u.nome",
      countQuery = "SELECT COUNT(u) FROM UF AS u WHERE u.postoList IS NOT EMPTY")
  Page<UF> findByActive(Pageable pageable);

}
