package com.certificadoranacional.ac.core.event;

import org.springframework.context.ApplicationEvent;

import com.certificadoranacional.ac.core.Constants;

public class VoucherEvent extends ApplicationEvent {

  private static final long serialVersionUID = Constants.VERSION;

  private final VoucherEventType type;

  public VoucherEvent(final String id, final VoucherEventType type) {
    super(id);
    this.type = type;
  }

  public String getId() {
    return (String) this.getSource();
  }

  public VoucherEventType getType() {
    return this.type;
  }

}
