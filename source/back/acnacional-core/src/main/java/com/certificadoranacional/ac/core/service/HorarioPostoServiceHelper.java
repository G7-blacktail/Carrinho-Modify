package com.certificadoranacional.ac.core.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import com.certificadoranacional.ac.core.utils.DateUtils;
import com.certificadoranacional.ac.jpa.entity.HorarioPosto;
import com.google.common.base.Preconditions;

public abstract class HorarioPostoServiceHelper {

  private HorarioPostoServiceHelper() {
    //
  }

  public static void check(final HorarioPosto horario) {
    LocalDateTime now = DateUtils.toLocalDateTime(new Date());
    LocalDateTime min = now.plusHours(4); // Minimo de 4 horas
    LocalDate localDate = DateUtils.toLocalDate(horario.getData());
    LocalTime localTime = DateUtils.toLocalTime(horario.getHorario().getInicio());
    LocalDateTime selected = LocalDateTime.of(localDate, localTime);

    Preconditions.checkState(now.isBefore(selected), "Horário selecionado inválido, este horário já foi ultrapassado");
    Preconditions.checkState(min.isBefore(selected), "Horário selecionado inválido, deve ser agendado com pelo menos 4 horas de antecedência");
  }

}
