package br.com.lidersis.legislista.jpa.test;

import java.time.LocalTime;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.certificadoranacional.ac.core.converter.LocalTimeConverter;

public class DateTest {

  public DateTest() {
    super();
  }

  @Test
  @Disabled
  public void test1() throws Exception {
    LocalTimeConverter converter = new LocalTimeConverter();
    LocalTime now = LocalTime.now();
    System.out.println(now);

    String str = converter.convert(now);
    System.out.println(str);

    now = converter.convertBack(str);
    System.out.println(now);
  }

  @Test
  public void test2() throws Exception {
    String data = "31/12/2017";
    System.out.println(data.substring(6) + data.substring(3, 5) + data.substring(0, 2));
  }

}
