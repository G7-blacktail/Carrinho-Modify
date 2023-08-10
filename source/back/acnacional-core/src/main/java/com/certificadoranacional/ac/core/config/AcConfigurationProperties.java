package com.certificadoranacional.ac.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "certificadoranacional.ac")
public class AcConfigurationProperties {

  private PagSeguroConfigurationProperties pagseguro;

  private StoreConfigurationProperties store;

  private ScheduleConfigurationProperties schedule;

  public AcConfigurationProperties() {
    super();
  }

  public ScheduleConfigurationProperties getSchedule() {
    return this.schedule;
  }

  public void setSchedule(final ScheduleConfigurationProperties schedule) {
    this.schedule = schedule;
  }

  public PagSeguroConfigurationProperties getPagseguro() {
    return this.pagseguro;
  }

  public void setPagseguro(final PagSeguroConfigurationProperties pagseguro) {
    this.pagseguro = pagseguro;
  }

  public StoreConfigurationProperties getStore() {
    return this.store;
  }

  public void setStore(final StoreConfigurationProperties store) {
    this.store = store;
  }

}
