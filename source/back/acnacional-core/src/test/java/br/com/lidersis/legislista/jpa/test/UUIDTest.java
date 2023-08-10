package br.com.lidersis.legislista.jpa.test;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class UUIDTest {

  public UUIDTest() {
    super();
  }

  @Test
  public void test4() throws Exception {
    UUID uuid = UUID.randomUUID();
    String id = uuid.toString();
    System.out.println(id);
    System.out.println(id.substring(0, 8));
  }

}
