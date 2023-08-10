package com.certificadoranacional.ac.core.event;

import org.springframework.context.ApplicationEvent;

import com.certificadoranacional.ac.core.Constants;

public class PedidoEvent extends ApplicationEvent {

  private static final long serialVersionUID = Constants.VERSION;

  private final PedidoEventType type;

  public PedidoEvent(final String id, final PedidoEventType type) {
    super(id);
    this.type = type;
  }

  public String getId() {
    return (String) this.getSource();
  }

  public PedidoEventType getType() {
    return this.type;
  }

}
