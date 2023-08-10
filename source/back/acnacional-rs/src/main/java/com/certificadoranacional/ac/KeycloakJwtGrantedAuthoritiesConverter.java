package com.certificadoranacional.ac;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class KeycloakJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  private static final String ROLE_PREFIX = "ROLE_";

  private String claim;

  private JwtGrantedAuthoritiesConverter delegate = new JwtGrantedAuthoritiesConverter();

  public KeycloakJwtGrantedAuthoritiesConverter(final String claim) {
    super();
    this.claim = claim;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Collection<GrantedAuthority> convert(final Jwt source) {
    Collection<GrantedAuthority> result = new HashSet<>(this.delegate.convert(source));
    Map<String, Object> claims = source.getClaims();
    if ((claims != null) && (claims.containsKey(this.claim))) {
      Object groupsObj = claims.get(this.claim);
      if (groupsObj instanceof Collection) {
        Collection<String> groupsCollection = (Collection<String>) groupsObj;
        for (String group : groupsCollection) {
          String name = group;
          if (!name.startsWith(KeycloakJwtGrantedAuthoritiesConverter.ROLE_PREFIX)) {
            name = KeycloakJwtGrantedAuthoritiesConverter.ROLE_PREFIX + name;
          }
          result.add(new SimpleGrantedAuthority(name));
          result.add(new SimpleGrantedAuthority(name.toUpperCase()));
        }
      }
    }
    return result;
  }

}
