package com.certificadoranacional.ac.web.resources.v10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.certificadoranacional.ac.core.model.AgendaRepresentation;
import com.certificadoranacional.ac.core.service.AgendaService;
import com.certificadoranacional.ac.web.resources.SecurityExpressions;

@RestController
@RequestMapping(path = "/api/v1.0/posto/{idPosto}/agenda")
@CrossOrigin
@PreAuthorize(SecurityExpressions.IS_AUTHENTICATED)
public class PostoAgendaResource {

  @Autowired
  private AgendaService agendaService;

  public PostoAgendaResource() {
    super();
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("idPosto") final String idPosto, @RequestParam(name = "mes", required = false) final Integer mes,
      @RequestParam(name = "ano", required = false) final Integer ano) {
    AgendaRepresentation rep = this.agendaService.get(idPosto, mes, ano);
    if (rep == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rep);
  }

}
