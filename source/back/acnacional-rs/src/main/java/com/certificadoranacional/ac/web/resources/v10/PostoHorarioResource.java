package com.certificadoranacional.ac.web.resources.v10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import com.certificadoranacional.ac.core.model.HorarioPostoRepresentation;
import com.certificadoranacional.ac.core.service.HorarioPostoService;
import com.certificadoranacional.ac.web.resources.SecurityExpressions;

@RestController
@RequestMapping(path = "/api/v1.0/posto/{idPosto}/horario")
@CrossOrigin
@PreAuthorize(SecurityExpressions.IS_AUTHENTICATED)
public class PostoHorarioResource {

  @Autowired
  private HorarioPostoService horarioPostoService;

  public PostoHorarioResource() {
    super();
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final String id) {
    HorarioPostoRepresentation rep = this.horarioPostoService.get(id);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rep);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody final HorarioPostoRepresentation item) {
    HorarioPostoRepresentation o = this.horarioPostoService.save(item);
    return ResponseEntity.ok(o);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@PathVariable("id") final String id) {
    HorarioPostoRepresentation rep = this.horarioPostoService.get(id);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    Boolean status = this.horarioPostoService.delete(id);
    if (!status.booleanValue()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rep);
    }
    return ResponseEntity.ok(rep);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@PathVariable("idPosto") final String idPosto, @RequestParam(name = "type", required = false) final String type,
      @RequestParam(name = "data", required = true) final String data, final Pageable pageable) {
    Boolean status = Boolean.valueOf("active".equals(type));
    return ResponseEntity.ok(this.horarioPostoService.listByIdPostoData(idPosto, data, status, pageable));
  }

}
