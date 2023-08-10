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

import com.certificadoranacional.ac.core.model.GrupoProdutoRepresentation;
import com.certificadoranacional.ac.core.service.GrupoProdutoService;
import com.certificadoranacional.ac.web.resources.SecurityExpressions;

@RestController
@RequestMapping(path = "/api/v1.0/grupo-produto")
@CrossOrigin
public class GrupoProdutoResource {

  @Autowired
  private GrupoProdutoService grupoProdutoService;

  public GrupoProdutoResource() {
    super();
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final String id) {
    GrupoProdutoRepresentation rep = this.grupoProdutoService.get(id);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rep);
  }

  @PreAuthorize(SecurityExpressions.ROLE_ADMIN)
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody final GrupoProdutoRepresentation item) {
    GrupoProdutoRepresentation o = this.grupoProdutoService.save(item);
    return ResponseEntity.ok(o);
  }

  @PreAuthorize(SecurityExpressions.ROLE_ADMIN)
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> put(@PathVariable("id") final String id, @RequestBody final GrupoProdutoRepresentation item) {
    item.setId(id);
    GrupoProdutoRepresentation o = this.grupoProdutoService.update(item);
    return ResponseEntity.ok(o);
  }

  @PreAuthorize(SecurityExpressions.ROLE_ADMIN)
  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@PathVariable("id") final String id) {
    Boolean b = this.grupoProdutoService.delete(id);
    if (!b.booleanValue()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(null);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@RequestParam(name = "tipo_id") final String tipo, @RequestParam(name = "type", required = false) final String type,
      final Pageable pageable) {
    if ("active".equals(type)) {
      return ResponseEntity.ok(this.grupoProdutoService.listByActive(tipo, pageable));
    }
    return ResponseEntity.ok(this.grupoProdutoService.list(tipo, pageable));
  }

}
