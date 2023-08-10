package com.certificadoranacional.ac.core.model;

import java.io.Serializable;

import com.certificadoranacional.ac.core.Constants;

public class ExceptionRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String message;

  private String stacktrace;

  public ExceptionRepresentation() {
    super();
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public String getStacktrace() {
    return this.stacktrace;
  }

  public void setStacktrace(final String stacktrace) {
    this.stacktrace = stacktrace;
  }

}
