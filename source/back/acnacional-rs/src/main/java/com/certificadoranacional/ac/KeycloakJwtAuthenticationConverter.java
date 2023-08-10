package com.certificadoranacional.ac;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

public class KeycloakJwtAuthenticationConverter extends JwtAuthenticationConverter {

  public KeycloakJwtAuthenticationConverter(final String claim) {
    super();
    this.setJwtGrantedAuthoritiesConverter(new KeycloakJwtGrantedAuthoritiesConverter(claim));
  }

}
