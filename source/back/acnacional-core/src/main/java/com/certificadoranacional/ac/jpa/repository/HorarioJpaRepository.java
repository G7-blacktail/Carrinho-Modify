package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.certificadoranacional.ac.jpa.entity.Horario;

public interface HorarioJpaRepository extends JpaRepository<Horario, String> {

  Horario findByCodigo(String codigo);

}
