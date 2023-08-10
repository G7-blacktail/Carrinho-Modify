package com.certificadoranacional.ac;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AcWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

  public AcWebSecurityConfigurer() {
    super();
  }

  @Bean(name = "authenticationManager")
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    // @formatter:off
    /*http
      .authorizeRequests()
        .antMatchers(
            "/api/v1.0/publico/**",
            "/api/v1.0/callback/**")
          .permitAll()
        .anyRequest()
          .authenticated();*/
    // @formatter:on
    http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {

      @Override
      public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // TODO Auto-generated method stub

      }

    });
  }

  @Override
  public void configure(final WebSecurity web) throws Exception {
    // @formatter:off
    //web
    //  .ignoring()
    //    .antMatchers(
    //      "/api/v1.0/publico/**"
    //    );
    // @formatter:on
  }

}
