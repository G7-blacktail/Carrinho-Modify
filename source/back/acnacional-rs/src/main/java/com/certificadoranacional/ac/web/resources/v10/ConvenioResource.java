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

import com.certificadoranacional.ac.core.model.ConvenioRepresentation;
import com.certificadoranacional.ac.core.service.ConvenioService;
import com.certificadoranacional.ac.web.resources.SecurityExpressions;

@RestController
@RequestMapping(path = "/api/v1.0/convenio")
@CrossOrigin
public class ConvenioResource {

  @Autowired
  private ConvenioService convenioService;

  public ConvenioResource() {
    super();
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final String id) {
    ConvenioRepresentation rep = this.convenioService.get(id);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rep);
  }

  @PreAuthorize(SecurityExpressions.ROLE_ADMIN)
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> done(@RequestBody final ConvenioRepresentation item) {
    ConvenioRepresentation o = this.convenioService.save(item);
    return ResponseEntity.ok(o);
  }

  @PreAuthorize(SecurityExpressions.ROLE_ADMIN)
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> put(@PathVariable("id") final String id, @RequestBody final ConvenioRepresentation item) {
    item.setId(id);
    ConvenioRepresentation o = this.convenioService.update(item);
    return ResponseEntity.ok(o);
  }

  @PreAuthorize(SecurityExpressions.ROLE_ADMIN)
  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@RequestParam(name = "q", required = false) final String query, final Pageable pageable) {
    return ResponseEntity.ok(this.convenioService.list(query, pageable));
  }

}
