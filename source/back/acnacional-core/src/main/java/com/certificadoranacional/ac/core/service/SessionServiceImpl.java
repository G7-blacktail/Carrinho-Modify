package com.certificadoranacional.ac.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.certificadoranacional.ac.core.model.ArUserDetails;
import com.certificadoranacional.ac.core.model.UsuarioRepresentation;
import com.google.common.base.Strings;

@Service
public class SessionServiceImpl implements SessionService {

  @Autowired
  private CacheManager cacheManager;

  @Autowired
  private UsuarioService usuarioService;

  public SessionServiceImpl() {
    super();
  }

  private ArUserDetails getPrincipal() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication instanceof UsernamePasswordAuthenticationToken) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        ArUserDetails details = (ArUserDetails) token.getPrincipal();
        return details;
      } else if (authentication instanceof RunAsUserToken) {
        RunAsUserToken token = (RunAsUserToken) authentication;
        ArUserDetails details = (ArUserDetails) token.getDetails();
        return details;
      } else if (authentication instanceof OAuth2Authentication) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        // OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails)
        // oAuth2Authentication.getDetails();
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        ArUserDetails details = (ArUserDetails) userAuthentication.getPrincipal();
        return details;
      } else if (authentication instanceof OAuth2AuthenticationToken) {
        OAuth2AuthenticationToken oAuth2Authentication = (OAuth2AuthenticationToken) authentication;
        ArUserDetails details = (ArUserDetails) oAuth2Authentication.getDetails();
        return details;
      } else if (authentication instanceof JwtAuthenticationToken) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        Jwt jwt = jwtAuthenticationToken.getToken();
        String id = jwt.getId();
        Cache cache = this.cacheManager.getCache("arlidersis_principal");
        ValueWrapper wrapper = cache.get(id);
        ArUserDetails details = null;
        if (wrapper == null) {
          String email = jwt.getClaimAsString("email");
          UsuarioRepresentation usuario = this.usuarioService.get(email);
          if (usuario != null) {
            details = new ArUserDetails(usuario.getId(), usuario.getEmail(), jwtAuthenticationToken.getAuthorities());
            cache.put(id, details);
          }
        } else {
          details = (ArUserDetails) wrapper.get();
        }
        return details;
      }
      return null;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public boolean isLogado() {
    String id = this.getIdUsuario();
    return !Strings.isNullOrEmpty(id);
  }

  @Override
  public boolean isAdministrador() {
    return this.isUserInRole("ADMIN");
  }

  @Override
  public boolean isGerente() {
    return this.isUserInRole("GERENTE");
  }

  @Override
  public boolean isAgente() {
    return this.isUserInRole("AGENTE");
  }

  private boolean isUserInRole(final String role) {
    ArUserDetails details = this.getPrincipal();
    if (details != null) {
      return details.isUserInRole(role);
    }
    return false;
  }

  @Override
  public UsuarioRepresentation getUsuario() {
    String id = this.getIdUsuario();
    if (id != null) {
      return this.usuarioService.get(id);
    }
    return null;
  }

  @Override
  public String getIdUsuario() {
    ArUserDetails details = this.getPrincipal();
    if (details != null) {
      return details.getId();
    }
    return null;
  }

}
