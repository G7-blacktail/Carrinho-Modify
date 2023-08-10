package com.certificadoranacional.ac;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.certificadoranacional.ac.core.Log;

@SpringBootApplication
public class AcApplication implements CommandLineRunner {

  @Override
  public void run(final String... args) throws Exception {
    Log.getLog().info("run...");
  }

  public static void main(String[] args) {
    SpringApplicationBuilder builder = new SpringApplicationBuilder(AcApplication.class);
    builder.headless(false);
    builder.sources(AcConfiguration.class);
    builder.web(WebApplicationType.SERVLET);
    builder.run(args);
  }
}
