package com.certificadoranacional.ac.core.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.Log;
import com.certificadoranacional.ac.core.service.NotificacaoEmailService;

@Component
public class VoucherEventListener implements ApplicationListener<VoucherEvent> {

  @Autowired
  private NotificacaoEmailService notificacaoEmailService;

  public VoucherEventListener() {
    super();
  }

  @Override
  public void onApplicationEvent(final VoucherEvent event) {
    Log.getLog().debug("Evento no pedido " + event.getId());
    if (event.getType() == VoucherEventType.AGENDADO) {
      this.notificacaoEmailService.enviarEmailAgendamentoVoucher(event.getId());
    }
  }

}
