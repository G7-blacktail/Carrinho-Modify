package com.certificadoranacional.ac;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class AcMeterRegistryConfiguration extends AbstractSwarmMeterConfiguration {

  public AcMeterRegistryConfiguration() {
    super();
  }

  @Bean
  @Override
  public MeterRegistryCustomizer<MeterRegistry> getMeterRegistryCustomizer(@Value("${spring.application.name}") final String applicationName) {
    return super.getMeterRegistryCustomizer(applicationName);
  }

}
