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

import com.certificadoranacional.ac.core.model.PostoRepresentation;
import com.certificadoranacional.ac.core.service.PostoService;
import com.certificadoranacional.ac.web.resources.SecurityExpressions;

@RestController
@RequestMapping(path = "/api/v1.0/posto")
@CrossOrigin
public class PostoResource {

  @Autowired
  private PostoService postoService;

  public PostoResource() {
    super();
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final String id) {
    PostoRepresentation rep = this.postoService.get(id);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rep);
  }

  @PreAuthorize(SecurityExpressions.IS_AUTHENTICATED)
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody final PostoRepresentation item) {
    PostoRepresentation o = this.postoService.save(item);
    return ResponseEntity.ok(o);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> put(@PathVariable("id") final String id, @RequestBody final PostoRepresentation item) {
    item.setId(id);
    PostoRepresentation o = this.postoService.update(item);
    return ResponseEntity.ok(o);
  }

  @PreAuthorize(SecurityExpressions.IS_AUTHENTICATED)
  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@PathVariable("id") final String id) {
    Boolean b = this.postoService.delete(id);
    if (!b.booleanValue()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(null);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@RequestParam(name = "idUf", required = false) final String idUf,
      @RequestParam(name = "idMunicipio", required = false) final String idMunicipio, @RequestParam(name = "type", required = false) final String type,
      @RequestParam(name = "q", required = false) final String query, final Pageable pageable) {
    Boolean status = Boolean.valueOf("active".equals(type));
    return ResponseEntity.ok(this.postoService.listByUfMunicipio(idUf, idMunicipio, status, query, pageable));
  }

}
