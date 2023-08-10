package com.certificadoranacional.ac.core.config;

public class ScheduleConfigurationProperties {

  private boolean enabled;

  private int initialDelay;

  private int fixedDelay;

  private int fixedRate;

  public ScheduleConfigurationProperties() {
    super();
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  public int getInitialDelay() {
    return this.initialDelay;
  }

  public void setInitialDelay(final int initialDelay) {
    this.initialDelay = initialDelay;
  }

  public int getFixedDelay() {
    return this.fixedDelay;
  }

  public void setFixedDelay(final int fixedDelay) {
    this.fixedDelay = fixedDelay;
  }

  public int getFixedRate() {
    return this.fixedRate;
  }

  public void setFixedRate(final int fixedRate) {
    this.fixedRate = fixedRate;
  }

}
