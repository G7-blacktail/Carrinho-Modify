package com.certificadoranacional.ac.core.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component
public class DecimalConverter extends AbstractConverter<BigDecimal, String> {

  private static final String FORMAT_BR = "#,##0.00";

  private static final Locale LOCALE_BR = new Locale("pt", "BR");

  public DecimalConverter() {
    super();
  }

  @Override
  public String convert(final BigDecimal obj) {
    if (obj == null) {
      return null;
    }
    DecimalFormat numberFormat = new DecimalFormat(DecimalConverter.FORMAT_BR, new DecimalFormatSymbols(DecimalConverter.LOCALE_BR));
    String rep = numberFormat.format(obj);
    return rep;
  }

  @Override
  public BigDecimal convertBack(final String rep) {
    if (Strings.isNullOrEmpty(rep)) {
      return null;
    }
    try {
      DecimalFormat numberFormat = new DecimalFormat(DecimalConverter.FORMAT_BR, new DecimalFormatSymbols(DecimalConverter.LOCALE_BR));
      Number number = numberFormat.parse(rep);
      BigDecimal obj = new BigDecimal(number.doubleValue());
      return obj;
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }
  }

}
