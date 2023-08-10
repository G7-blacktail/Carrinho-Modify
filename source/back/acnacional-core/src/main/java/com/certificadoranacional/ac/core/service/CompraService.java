package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;

import com.certificadoranacional.ac.core.model.ConvenioRepresentation;
import com.certificadoranacional.ac.core.model.PedidoProdutoRepresentation;
import com.certificadoranacional.ac.core.model.PedidoRepresentation;

public interface CompraService {

  PedidoRepresentation get(String id);

  PedidoRepresentation begin();

  PedidoRepresentation add(PedidoProdutoRepresentation rep);

  PedidoProdutoRepresentation addSimple(PedidoProdutoRepresentation rep);

  PedidoRepresentation addConvenio(String id, ConvenioRepresentation rep);

  PedidoRepresentation update(PedidoProdutoRepresentation rep);

  PedidoProdutoRepresentation updateSimple(PedidoProdutoRepresentation rep);

  PedidoRepresentation remove(String id);

  PedidoRepresentation removeConvenio(String id);

  PedidoRepresentation end(PedidoRepresentation rep);

  Boolean delete(String id);

  Page<PedidoRepresentation> list();

}
