package com.certificadoranacional.ac.web.resources.v10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.certificadoranacional.ac.core.model.FuncionarioRepresentation;
import com.certificadoranacional.ac.core.service.FuncionarioService;
import com.certificadoranacional.ac.web.resources.SecurityExpressions;

@RestController
@RequestMapping(path = "/api/v1.0/posto/{idPosto}/funcionario")
@CrossOrigin
@PreAuthorize(SecurityExpressions.ROLE_ADMIN)
public class PostoFuncionarioResource {

  @Autowired
  private FuncionarioService funcionarioService;

  public PostoFuncionarioResource() {
    super();
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final String id) {
    FuncionarioRepresentation rep = this.funcionarioService.get(id);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rep);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody final FuncionarioRepresentation item) {
    FuncionarioRepresentation o = this.funcionarioService.save(item);
    return ResponseEntity.ok(o);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@PathVariable("id") final String id) {
    this.funcionarioService.delete(id);
    return ResponseEntity.ok(null);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@PathVariable("idPosto") final String idPosto, @RequestParam(name = "q", required = false) final String query,
      final Pageable pageable) {
    return ResponseEntity.ok(this.funcionarioService.listByIdPosto(idPosto, query, pageable));
  }

}
