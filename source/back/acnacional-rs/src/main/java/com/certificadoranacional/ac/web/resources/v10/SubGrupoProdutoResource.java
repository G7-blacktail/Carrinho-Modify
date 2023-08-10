package com.certificadoranacional.ac.web.resources.v10;

import java.util.List;

import com.certificadoranacional.ac.core.model.GrupoProdutoRepresentation;
import com.certificadoranacional.ac.core.model.SubGrupoProdutoRepresentation;
import com.certificadoranacional.ac.core.service.GrupoProdutoService;
import com.certificadoranacional.ac.core.service.SubGrupoProdutoService;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;
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
@RequestMapping(path = "/api/v1.0/subgrupo-produto")
@CrossOrigin
public class SubGrupoProdutoResource {

  @Autowired
  private SubGrupoProdutoService subGrupoProdutoService;

  @Autowired
  private GrupoProdutoService grupoProdutoService;

  public SubGrupoProdutoResource() {
    super();
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final String id, @RequestParam(name = "grupo_id", required = false) final String grupo) {
    SubGrupoProdutoRepresentation rep = this.subGrupoProdutoService.get(id);
    if (rep == null) {
      if (!Strings.isNullOrEmpty(grupo)) {
        GrupoProdutoRepresentation gp = this.grupoProdutoService.get(grupo);
        if (gp != null) {
          String idToFind = null;
          List<SubGrupoProdutoRepresentation> list = this.subGrupoProdutoService.list(gp.getId(), SpringRepositoryHelper.ALL_PAGEABLE).getContent();
          for (SubGrupoProdutoRepresentation i : list) {
            if ((i.getId().equals(id)) || (i.getCodigo().equals(id))) {
              idToFind = i.getId();
              break;
            }
          }
          if (idToFind != null) {
            rep = this.subGrupoProdutoService.get(idToFind);
          }

        }
      }
      if (rep == null) {
        return ResponseEntity.notFound().build();
      }
    }
    return ResponseEntity.ok(rep);
  }

  @PreAuthorize(SecurityExpressions.ROLE_ADMIN)
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody final SubGrupoProdutoRepresentation item) {
    SubGrupoProdutoRepresentation o = this.subGrupoProdutoService.save(item);
    return ResponseEntity.ok(o);
  }

  @PreAuthorize(SecurityExpressions.ROLE_ADMIN)
  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> put(@PathVariable("id") final String id, @RequestBody final SubGrupoProdutoRepresentation item) {
    item.setId(id);
    SubGrupoProdutoRepresentation o = this.subGrupoProdutoService.update(item);
    return ResponseEntity.ok(o);
  }

  @PreAuthorize(SecurityExpressions.ROLE_ADMIN)
  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@PathVariable("id") final String id) {
    Boolean b = this.subGrupoProdutoService.delete(id);
    if (!b.booleanValue()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(null);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@RequestParam(name = "grupo_id") final String grupo, @RequestParam(name = "type", required = false) final String type,
      final Pageable pageable) {
    if ("active".equals(type)) {
      return ResponseEntity.ok(this.subGrupoProdutoService.listByActive(grupo, pageable));
    }
    return ResponseEntity.ok(this.subGrupoProdutoService.list(grupo, pageable));
  }

}
