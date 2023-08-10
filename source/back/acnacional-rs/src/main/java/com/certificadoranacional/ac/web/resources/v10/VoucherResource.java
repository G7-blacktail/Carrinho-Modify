package com.certificadoranacional.ac.web.resources.v10;

import com.certificadoranacional.ac.core.model.VoucherRepresentation;
import com.certificadoranacional.ac.core.service.VoucherService;
import com.certificadoranacional.ac.web.resources.SecurityExpressions;
import com.google.common.base.Strings;

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

@RestController
@RequestMapping(path = "/api/v1.0/voucher")
@CrossOrigin
@PreAuthorize(SecurityExpressions.IS_AUTHENTICATED)
public class VoucherResource {

  @Autowired
  private VoucherService voucherService;

  public VoucherResource() {
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
  public ResponseEntity<?> list(@RequestParam(name = "q", required = false) final String query,
      @RequestParam(name = "type", required = false) final String type, @RequestParam(name = "idPedido", required = false) final String idPedido,
      final Pageable pageable) {
    if (!Strings.isNullOrEmpty(idPedido)) {
      return ResponseEntity.ok(this.voucherService.listByIdPedido(idPedido, query, pageable));
    }
    Boolean status = Boolean.valueOf("active".equals(type));
    return ResponseEntity.ok(this.voucherService.listByStatus(query, status, pageable));
  }

}
