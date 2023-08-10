package com.certificadoranacional.ac.web.resources.v10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.certificadoranacional.ac.core.model.TipoProdutoRepresentation;
import com.certificadoranacional.ac.core.service.TipoProdutoService;

@RestController
@RequestMapping(path = "/api/v1.0/tipo-produto")
@CrossOrigin
public class TipoProdutoResource {

  @Autowired
  private TipoProdutoService tipoProdutoService;

  public TipoProdutoResource() {
    super();
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final String id) {
    TipoProdutoRepresentation rep = this.tipoProdutoService.get(id);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rep);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@RequestParam(name = "type", required = false) final String type, final Pageable pageable) {
    if ("active".equals(type)) {
      return ResponseEntity.ok(this.tipoProdutoService.listByActive(pageable));
    }
    return ResponseEntity.ok(this.tipoProdutoService.list(pageable));
  }

}
