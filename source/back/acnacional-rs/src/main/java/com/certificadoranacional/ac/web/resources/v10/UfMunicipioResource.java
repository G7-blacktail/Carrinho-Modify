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

import com.certificadoranacional.ac.core.model.MunicipioRepresentation;
import com.certificadoranacional.ac.core.service.MunicipioService;

@RestController
@RequestMapping(path = "/api/v1.0/uf/{uf}/municipio")
@CrossOrigin
public class UfMunicipioResource {

  @Autowired
  private MunicipioService municipioService;

  public UfMunicipioResource() {
    super();
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final String id) {
    MunicipioRepresentation rep = this.municipioService.get(id);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rep);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@PathVariable("uf") final String uf, @RequestParam(name = "type", required = false) final String type,
      @RequestParam(name = "q", required = false) final String query, final Pageable pageable) {
    Boolean status = Boolean.valueOf("active".equals(type));
    return ResponseEntity.ok(this.municipioService.listByUf(uf, status, query, pageable));
  }

}
