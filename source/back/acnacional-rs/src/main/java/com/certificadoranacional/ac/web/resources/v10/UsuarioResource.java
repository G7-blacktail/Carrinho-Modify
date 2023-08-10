package com.certificadoranacional.ac.web.resources.v10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.certificadoranacional.ac.core.model.UsuarioRepresentation;
import com.certificadoranacional.ac.core.service.UsuarioService;
import com.certificadoranacional.ac.web.resources.SecurityExpressions;

@RestController
@RequestMapping(path = "/api/v1.0/usuario")
@CrossOrigin
public class UsuarioResource {

  @Autowired
  private UsuarioService usuarioService;

  public UsuarioResource() {
    super();
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final String id) {
    UsuarioRepresentation rep = this.usuarioService.get(id);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rep);
  }

  @PreAuthorize(SecurityExpressions.ROLE_ADMIN)
  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@RequestParam(name = "q", required = false) final String query, final Pageable pageable) {
    Page<UsuarioRepresentation> result = this.usuarioService.list(query, pageable);
    return ResponseEntity.ok(result);
  }

}
