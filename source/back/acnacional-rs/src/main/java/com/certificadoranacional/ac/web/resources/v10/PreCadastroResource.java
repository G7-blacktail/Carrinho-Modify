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

import com.certificadoranacional.ac.core.model.PreCadastroRepresentation;
import com.certificadoranacional.ac.core.service.PreCadastroService;
import com.certificadoranacional.ac.web.resources.SecurityExpressions;

@RestController
@RequestMapping(path = "/api/v1.0/pre-cadastro")
@CrossOrigin
public class PreCadastroResource {

  @Autowired
  private PreCadastroService preCadastroService;

  public PreCadastroResource() {
    super();
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final String id) {
    PreCadastroRepresentation rep = this.preCadastroService.get(id);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rep);
  }

  @PreAuthorize(SecurityExpressions.IS_AUTHENTICATED)
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody final PreCadastroRepresentation item) {
    PreCadastroRepresentation o = this.preCadastroService.save(item);
    return ResponseEntity.ok(o);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> put(@PathVariable("id") final String id, @RequestBody final PreCadastroRepresentation item) {
    item.setId(id);
    PreCadastroRepresentation o = this.preCadastroService.update(item);
    return ResponseEntity.ok(o);
  }

  @PreAuthorize(SecurityExpressions.IS_AUTHENTICATED)
  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@PathVariable("id") final String id) {
    Boolean b = this.preCadastroService.delete(id);
    if (!b.booleanValue()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(null);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@RequestParam(name = "idCliente", required = false) final String idCliente,
      @RequestParam(name = "tipo", required = false) final Integer tipo, final Pageable pageable) {
    return ResponseEntity.ok(this.preCadastroService.listByCliente(idCliente, tipo, pageable));
  }

}
