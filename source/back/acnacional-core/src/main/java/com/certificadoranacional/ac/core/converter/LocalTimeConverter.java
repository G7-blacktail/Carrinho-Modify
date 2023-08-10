package com.certificadoranacional.ac.core.converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component
public class LocalTimeConverter extends AbstractConverter<LocalTime, String> {

  private static final String FORMAT_BR = "HH:mm";

  private DateTimeFormatter formatter;

  public LocalTimeConverter() {
    super();
    // @formatter:off
    this.formatter = new DateTimeFormatterBuilder()
        .appendPattern(LocalTimeConverter.FORMAT_BR)
        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
        .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
        .parseDefaulting(ChronoField.YEAR, 2000)
        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
        .toFormatter();
    // @formatter:on
  }

  @Override
  public String convert(final LocalTime obj) {
    if (obj == null) {
      return null;
    }
    String rep = this.formatter.format(obj);
    return rep;
  }

  @Override
  public LocalTime convertBack(final String rep) {
    if (Strings.isNullOrEmpty(rep)) {
      return null;
    }
    LocalTime obj = LocalTime.parse(rep, this.formatter);
    return obj;
  }

}
