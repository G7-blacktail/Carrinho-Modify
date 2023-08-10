package com.certificadoranacional.ac.web.resources.v10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.certificadoranacional.ac.core.Log;
import com.certificadoranacional.ac.core.model.ConvenioRepresentation;
import com.certificadoranacional.ac.core.model.PedidoProdutoRepresentation;
import com.certificadoranacional.ac.core.model.PedidoRepresentation;
import com.certificadoranacional.ac.core.service.CompraService;
import com.certificadoranacional.ac.web.resources.SecurityExpressions;

@RestController
@RequestMapping(path = "/api/v1.0/compra")
@CrossOrigin
public class CompraResource {

  @Autowired
  private CompraService compraService;

  public CompraResource() {
    super();
  }

  @RequestMapping(path = "/begin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> begin() {
    PedidoRepresentation rep = this.compraService.begin();
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().cacheControl(CacheControl.noCache().mustRevalidate().cachePrivate()).body(rep);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final String id) {
    PedidoRepresentation rep = this.compraService.get(id);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rep);
  }

  @RequestMapping(path = "/{id}/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> add(@PathVariable("id") final String id, @RequestBody final PedidoProdutoRepresentation item) {
    item.setPedido(new PedidoRepresentation());
    item.getPedido().setId(id);
    PedidoRepresentation o = this.compraService.add(item);
    return ResponseEntity.ok(o);
  }

  @RequestMapping(path = "/{id}/add-simple", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addSimple(@PathVariable("id") final String id, @RequestBody final PedidoProdutoRepresentation item) {
    item.setPedido(new PedidoRepresentation());
    item.getPedido().setId(id);
    PedidoProdutoRepresentation o = this.compraService.addSimple(item);
    return ResponseEntity.ok(o);
  }

  @RequestMapping(path = "/{id}/add-convenio", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addConvenio(@PathVariable("id") final String id, @RequestBody final ConvenioRepresentation item) {
    PedidoRepresentation o = this.compraService.addConvenio(id, item);
    return ResponseEntity.ok(o);
  }

  @RequestMapping(path = "/{id}/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> update(@PathVariable("id") final String id, @RequestBody final PedidoProdutoRepresentation item) {
    item.setPedido(new PedidoRepresentation());
    item.getPedido().setId(id);
    PedidoRepresentation o = this.compraService.update(item);
    return ResponseEntity.ok(o);
  }

  @RequestMapping(path = "/{id}/update-simple", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateSimple(@PathVariable("id") final String id, @RequestBody final PedidoProdutoRepresentation item) {
    item.setPedido(new PedidoRepresentation());
    item.getPedido().setId(id);
    PedidoProdutoRepresentation o = this.compraService.updateSimple(item);
    return ResponseEntity.ok(o);
  }

  @RequestMapping(path = "/{id}/remove/{itemId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> remove(@PathVariable("id") final String id, @PathVariable("itemId") final String itemId) {
    Log.getLog().debug(id); // FIXME
    PedidoRepresentation o = this.compraService.remove(itemId);
    return ResponseEntity.ok(o);
  }

  @RequestMapping(path = "/{id}/remove-convenio", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> removeConvenio(@PathVariable("id") final String id) {
    Log.getLog().debug(id); // FIXME
    PedidoRepresentation o = this.compraService.removeConvenio(id);
    return ResponseEntity.ok(o);
  }

  @PreAuthorize(SecurityExpressions.IS_AUTHENTICATED)
  @RequestMapping(path = "/end", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> end(@RequestBody final PedidoRepresentation item) {
    PedidoRepresentation o = this.compraService.end(item);
    return ResponseEntity.ok(o);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@PathVariable("id") final String id) {
    Boolean b = this.compraService.delete(id);
    if (b.booleanValue()) {
      ResponseEntity.ok();
    }
    return ResponseEntity.notFound().build();
  }

}
