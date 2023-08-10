package com.certificadoranacional.ac.core.converter;

import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component
public class IntegerConverter extends AbstractConverter<Integer, String> {

  public IntegerConverter() {
    super();
  }

  @Override
  public String convert(final Integer obj) {
    if (obj != null) {
      return obj.toString();
    }
    return null;
  }

  @Override
  public Integer convertBack(final String rep) {
    if (!Strings.isNullOrEmpty(rep)) {
      return Integer.valueOf(rep);
    }
    return null;
  }

}
