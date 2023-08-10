package com.certificadoranacional.ac.core.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component
public class LocalDateConverter extends AbstractConverter<LocalDate, String> {

  private static final String FORMAT_BR = "dd/MM/yyyy";

  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LocalDateConverter.FORMAT_BR);

  public LocalDateConverter() {
    super();
  }

  @Override
  public String convert(final LocalDate obj) {
    if (obj == null) {
      return null;
    }
    String rep = this.formatter.format(obj);
    return rep;
  }

  @Override
  public LocalDate convertBack(final String rep) {
    if (Strings.isNullOrEmpty(rep)) {
      return null;
    }
    LocalDate obj = LocalDate.parse(rep, this.formatter);
    return obj;
  }

}
