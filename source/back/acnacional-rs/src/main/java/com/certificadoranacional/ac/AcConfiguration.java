package com.certificadoranacional.ac;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.certificadoranacional.ac.core.config.AcConfigurationProperties;

@Configuration
@ComponentScan(basePackages = {"com.certificadoranacional.ac"})
@EnableAsync
@EnableCaching
@EnableConfigurationProperties(AcConfigurationProperties.class)
@EnableJpaRepositories
@EnableOAuth2Sso
@EnableScheduling
@EnableTransactionManagement
@EnableWebMvc
public class AcConfiguration {

  public AcConfiguration() {
    super();
  }

}
