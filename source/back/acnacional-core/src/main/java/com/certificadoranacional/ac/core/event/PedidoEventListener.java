package com.certificadoranacional.ac.core.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.Log;
import com.certificadoranacional.ac.core.service.NotificacaoEmailService;

@Component
public class PedidoEventListener implements ApplicationListener<PedidoEvent> {

  @Autowired
  private NotificacaoEmailService notificacaoEmailService;

  public PedidoEventListener() {
    super();
  }

  @Override
  public void onApplicationEvent(final PedidoEvent event) {
    Log.getLog().debug("Evento no pedido " + event.getId());
    if (event.getType() == PedidoEventType.PAGO) {
      this.notificacaoEmailService.enviarEmailConfirmacaoPagamento(event.getId());
    }
  }

}
