package br.com.lidersis.legislista.jpa.test;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.certificadoranacional.ac.core.converter.DecimalConverter;

public class DecimalConverterTest {

  public DecimalConverterTest() {
    super();
  }

  @Test
  public void testConvert() throws Exception {
    DecimalConverter converter = new DecimalConverter();
    // Double value = new BigDecimal(1234.56);
    BigDecimal value = new BigDecimal(150);
    String str = converter.convert(value);
    System.out.println(value);
    System.out.println(str);

    value = converter.convertBack(str);
    str = converter.convert(value);
    System.out.println(value);
    System.out.println(str);
  }

}
