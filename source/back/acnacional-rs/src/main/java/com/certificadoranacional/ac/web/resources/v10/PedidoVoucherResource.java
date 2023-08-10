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

import com.certificadoranacional.ac.core.model.VoucherRepresentation;
import com.certificadoranacional.ac.core.service.VoucherService;
import com.certificadoranacional.ac.web.resources.SecurityExpressions;

@RestController
@RequestMapping(path = "/api/v1.0/pedido/{idPedido}/voucher")
@CrossOrigin
@PreAuthorize(SecurityExpressions.IS_AUTHENTICATED)
public class PedidoVoucherResource {

  @Autowired
  private VoucherService voucherService;

  public PedidoVoucherResource() {
    super();
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final String id) {
    VoucherRepresentation rep = this.voucherService.get(id);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rep);
  }

  @RequestMapping(path = "/schedule", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> schedule(@RequestBody final VoucherRepresentation item) {
    VoucherRepresentation o = this.voucherService.schedule(item);
    return ResponseEntity.ok(o);
  }

  @RequestMapping(path = "/done", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> done(@RequestBody final VoucherRepresentation item) {
    VoucherRepresentation o = this.voucherService.done(item);
    return ResponseEntity.ok(o);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@PathVariable("idPedido") final String parentId, @RequestParam(name = "q", required = false) final String query,
      final Pageable pageable) {
    return ResponseEntity.ok(this.voucherService.listByIdPedido(parentId, query, pageable));
  }

}
