package com.certificadoranacional.ac.core.service;

import com.certificadoranacional.ac.jpa.entity.Pedido;

public interface PagSeguroService {

  String send(Pedido item);

  Boolean checkReference(String id);

  Boolean checkTransaction(String id);

  Boolean checkNotification(String id);

  String getUrl(String code);

}
