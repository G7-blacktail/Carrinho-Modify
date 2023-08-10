package com.certificadoranacional.ac.core.config;

public class PagSeguroConfigurationProperties {

  private String email;

  private String token;

  private String env;

  public PagSeguroConfigurationProperties() {
    super();
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(final String token) {
    this.token = token;
  }

  public String getEnv() {
    return this.env;
  }

  public void setEnv(final String env) {
    this.env = env;
  }

}
