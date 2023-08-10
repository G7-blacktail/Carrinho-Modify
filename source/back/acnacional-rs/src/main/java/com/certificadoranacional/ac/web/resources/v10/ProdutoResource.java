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

import com.certificadoranacional.ac.core.model.ProdutoRepresentation;
import com.certificadoranacional.ac.core.service.ProdutoService;
import com.certificadoranacional.ac.web.resources.SecurityExpressions;

@RestController
@RequestMapping(path = "/api/v1.0/produto")
@CrossOrigin
public class ProdutoResource {

  @Autowired
  private ProdutoService produtoService;

  public ProdutoResource() {
    super();
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final String id) {
    ProdutoRepresentation rep = this.produtoService.get(id);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rep);
  }

  @PreAuthorize(SecurityExpressions.ROLE_ADMIN)
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody final ProdutoRepresentation item) {
    ProdutoRepresentation o = this.produtoService.save(item);
    return ResponseEntity.ok(o);
  }

  @PreAuthorize(SecurityExpressions.ROLE_ADMIN)
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> put(@PathVariable("id") final String id, @RequestBody final ProdutoRepresentation item) {
    item.setId(id);
    ProdutoRepresentation o = this.produtoService.update(item);
    return ResponseEntity.ok(o);
  }

  @PreAuthorize(SecurityExpressions.ROLE_ADMIN)
  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@PathVariable("id") final String id) {
    Boolean b = this.produtoService.delete(id);
    if (!b.booleanValue()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(null);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@RequestParam(name = "tipo_id", required = false) final String tipo,
      @RequestParam(name = "grupo_id", required = false) final String grupo, @RequestParam(name = "subgrupo_id", required = false) final String subgrupo,
      @RequestParam(name = "type", required = false) final String type, @RequestParam(name = "q", required = false) final String query,
      final Pageable pageable) {
    Boolean status = Boolean.valueOf("active".equals(type));
    return ResponseEntity.ok(this.produtoService.list(tipo, grupo, subgrupo, query, status, pageable));
  }

}
