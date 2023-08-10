package com.certificadoranacional.ac.jpa.repository;

import java.sql.Time;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.certificadoranacional.ac.jpa.entity.HorarioPosto;

public interface HorarioPostoJpaRepository extends JpaRepository<HorarioPosto, String> {

  HorarioPosto findByPostoIdAndHorarioIdAndDataTx(String idPosto, String idHorario, String dataTx);

  Page<HorarioPosto> findByPostoIdAndDataTxAndHorarioInicioGreaterThanOrderByHorarioInicioAsc(String id, String data, Time time, Pageable pageable);

  Page<HorarioPosto> findByPostoIdAndDataTxBetweenOrderByDataTxAscHorarioInicioAsc(String id, String start, String end, Pageable pageable);

}
