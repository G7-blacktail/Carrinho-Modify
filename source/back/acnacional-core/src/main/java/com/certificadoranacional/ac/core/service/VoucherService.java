package com.certificadoranacional.ac.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.certificadoranacional.ac.core.model.VoucherRepresentation;

public interface VoucherService {

  VoucherRepresentation get(String id);

  VoucherRepresentation save(VoucherRepresentation rep);

  VoucherRepresentation update(VoucherRepresentation rep);

  VoucherRepresentation schedule(VoucherRepresentation rep);

  VoucherRepresentation done(VoucherRepresentation rep);

  Boolean delete(String id);

  Page<VoucherRepresentation> list(String filter, Pageable pageable);
  
  Page<VoucherRepresentation> listByStatus(String filter, Boolean status, Pageable pageable);

  Page<VoucherRepresentation> listByIdPedido(String idPedido, String filter, Pageable pageable);

  Page<VoucherRepresentation> listByData(String data, Pageable pageable);

}
