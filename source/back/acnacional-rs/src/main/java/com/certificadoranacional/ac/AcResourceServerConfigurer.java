package com.certificadoranacional.ac;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class AcResourceServerConfigurer extends ResourceServerConfigurerAdapter {

  public AcResourceServerConfigurer() {
    super();
  }

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .oauth2ResourceServer()
        .jwt()
          .jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter("authorities")) // Usado para adicionar roles para o usuario
      .and()
    .and()
      .authorizeRequests()
        //.antMatchers("/api/v1.0/pagseguro/**")
        //   .permitAll()
        .anyRequest()
          // .authenticated();
          .permitAll();
    // @formatter:on
  }

  @Override
  public void configure(final ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(null);
  }

}
