package com.certificadoranacional.ac.core.schedule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.Log;
import com.certificadoranacional.ac.core.model.VoucherRepresentation;
import com.certificadoranacional.ac.core.service.NotificacaoEmailService;
import com.certificadoranacional.ac.core.service.VoucherService;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;

@Component
@ConditionalOnProperty(name = "certificadoranacional.ac.schedule.enabled", havingValue = "true")
public class VoucherAgendadoSchedule {

  @Autowired
  private VoucherService voucherService;

  @Autowired
  private NotificacaoEmailService notificacaoEmailService;

  public VoucherAgendadoSchedule() {
    super();
  }

  @Scheduled(cron = "0 0 10 * * *")
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public void execute() {
    LocalDate date = LocalDate.now().plusDays(1);
    Integer year = Integer.valueOf(date.getYear());
    Integer month = Integer.valueOf(date.getMonthValue());
    Integer day = Integer.valueOf(date.getDayOfMonth());
    String dataTx = String.format("%04d%02d%02d", year, month, day);
    Page<VoucherRepresentation> page = this.voucherService.listByData(dataTx, SpringRepositoryHelper.ALL_PAGEABLE);
    List<VoucherRepresentation> list = page.getContent();
    if (!list.isEmpty()) {
      for (VoucherRepresentation item : list) {
        try {
          this.notificacaoEmailService.enviarEmailAgendamentoVoucher(item.getId());
        } catch (Exception e) {
          Log.getLog().info(e.getMessage(), e);
        }
      }
    }
  }

}
