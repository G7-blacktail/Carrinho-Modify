package com.certificadoranacional.ac.core.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.Log;
import com.certificadoranacional.ac.core.utils.DateUtils;
import com.certificadoranacional.ac.jpa.entity.Horario;
import com.certificadoranacional.ac.jpa.entity.HorarioPosto;
import com.certificadoranacional.ac.jpa.entity.Posto;
import com.certificadoranacional.ac.jpa.repository.HorarioJpaRepository;
import com.certificadoranacional.ac.jpa.repository.HorarioPostoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.PostoJpaRepository;

@Component
@ConditionalOnProperty(name = "certificadoranacional.ac.schedule.enabled", havingValue = "true")
public class HorarioSchedule {

  @Autowired
  private HorarioJpaRepository horarioJpaRepository;

  @Autowired
  private HorarioPostoJpaRepository horarioPostoJpaRepository;

  @Autowired
  private PostoJpaRepository postoJpaRepository;

  public HorarioSchedule() {
    super();
  }

  @Scheduled(initialDelay = 5000, fixedDelay = 86400000) // 1 day
  @Transactional(propagation = Propagation.REQUIRED)
  public void executeHorario() {
    try {
      Log.getLog().info("Executando o job do horario");
      LocalTime start = LocalTime.of(9, 0);
      while (start.getHour() < 18) {
        LocalTime end = start.plusMinutes(30);
        if ((start.getHour() < 12) || (start.getHour() > 13)) {
          Integer horaInicio = Integer.valueOf(start.getHour());
          Integer minutoInicio = Integer.valueOf(start.getMinute());
          Integer horaFim = Integer.valueOf(end.getHour());
          Integer minutoFim = Integer.valueOf(end.getMinute());
          String codigo = String.format("%02d%02d-%02d%02d", horaInicio, minutoInicio, horaFim, minutoFim);
          String descricao = String.format("%02d:%02d às %02d:%02d", horaInicio, minutoInicio, horaFim, minutoFim);

          Horario horario = this.horarioJpaRepository.findByCodigo(codigo);
          if (horario == null) {
            Log.getLog().info("Salvando horario " + codigo);
            horario = new Horario();
            horario.setCodigo(codigo);
            horario.setDescricao(descricao);
            horario.setFim(DateUtils.toTime(end));
            horario.setInicio(DateUtils.toTime(start));
            this.horarioJpaRepository.save(horario);
          }
        }
        start = end;
      }
      this.horarioJpaRepository.flush();
    } catch (Exception e) {
      Log.getLog().error(e.getMessage(), e);
      throw new IllegalStateException(e);
    }
  }

  @Scheduled(initialDelay = 30000, fixedDelay = 3600000) // 1 hour
  @Transactional(propagation = Propagation.REQUIRED)
  public void executeHorarioPosto() {
    try {
      Log.getLog().info("Executando o job do horario do posto");
      List<Posto> postoList = this.postoJpaRepository.findAll();
      List<Horario> horarioList = this.horarioJpaRepository.findAll();
      LocalDate start = LocalDate.now();
      LocalDate end = start.plusMonths(1);
      while (start.isBefore(end)) {
        for (Posto posto : postoList) {
          DayOfWeek dayOfWeek = start.getDayOfWeek();
          if (dayOfWeek.getValue() < 6) {
            for (Horario horario : horarioList) {
              LocalTime horarioInicio = DateUtils.toLocalTime(horario.getInicio());
              LocalTime horarioFim = DateUtils.toLocalTime(horario.getFim());
              if ((horarioInicio.getHour() < 9) || (horarioInicio.getHour() > 18) || (horarioFim.getHour() > 18)) {
                continue;
              }
              if ((horarioInicio.getHour() < 12) || (horarioInicio.getHour() > 13)) {
                Integer year = Integer.valueOf(start.getYear());
                Integer month = Integer.valueOf(start.getMonthValue());
                Integer day = Integer.valueOf(start.getDayOfMonth());
                String dataTx = String.format("%04d%02d%02d", year, month, day);
                HorarioPosto horarioPosto = this.horarioPostoJpaRepository.findByPostoIdAndHorarioIdAndDataTx(posto.getId(), horario.getId(), dataTx);
                if (horarioPosto == null) {
                  Log.getLog().info("Salvando o horario do posto " + posto.getCodigo() + " => " + horario.getCodigo());
                  horarioPosto = new HorarioPosto();
                  horarioPosto.setDisponivel(Boolean.TRUE);
                  horarioPosto.setData(DateUtils.toDate(start));
                  horarioPosto.setDataTx(dataTx);
                  horarioPosto.setEvento(null);
                  horarioPosto.setHorario(horario);
                  horarioPosto.setPosto(posto);
                  horarioPosto.setTipo(HorarioPosto.TIPO_DISPONIVEL);
                  this.horarioPostoJpaRepository.save(horarioPosto);
                }
              }
            }
          }
        }
        start = start.plusDays(1);
      }
      this.horarioPostoJpaRepository.flush();
    } catch (Exception e) {
      Log.getLog().error(e.getMessage(), e);
      throw new IllegalStateException(e);
    }
  }

}
