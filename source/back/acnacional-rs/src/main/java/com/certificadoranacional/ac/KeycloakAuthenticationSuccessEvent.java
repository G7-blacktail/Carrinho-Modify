package com.certificadoranacional.ac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.Log;
import com.certificadoranacional.ac.core.model.UsuarioRepresentation;
import com.certificadoranacional.ac.core.service.UsuarioService;

@Component
public class KeycloakAuthenticationSuccessEvent implements ApplicationListener<AbstractAuthenticationEvent> {

  @Autowired
  private UsuarioService usuarioService;

  public KeycloakAuthenticationSuccessEvent() {
    super();
  }

  @Override
  public void onApplicationEvent(final AbstractAuthenticationEvent event) {
    Authentication authentication = event.getAuthentication();
    if (authentication instanceof JwtAuthenticationToken) {
      try {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        Jwt jwt = jwtAuthenticationToken.getToken();
        String email = jwt.getClaimAsString("email");
        String nome = jwt.getClaimAsString("name");
        UsuarioRepresentation usuario = this.usuarioService.get(email);
        if (usuario == null) {
          usuario = new UsuarioRepresentation();
          usuario.setEmail(email);
          usuario.setNome(nome);
          this.usuarioService.save(usuario);
        }
      } catch (Exception e) {
        Log.getLog().warn(e.getMessage(), e);
      }
    }
  }

}
