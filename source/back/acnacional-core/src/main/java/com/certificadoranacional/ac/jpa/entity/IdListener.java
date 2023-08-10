package com.certificadoranacional.ac.jpa.entity;

import java.util.UUID;

import javax.persistence.PrePersist;

import com.certificadoranacional.ac.core.Log;
import com.google.common.base.Strings;

public class IdListener {

  public IdListener() {
    super();
  }

  @PrePersist
  public void prePersist(final AbstractEntity abstractEntity) {
    if (Strings.isNullOrEmpty(abstractEntity.getId())) {
      Log.getLog().info("Gerando ID para " + abstractEntity.getClass());
      abstractEntity.setId(UUID.randomUUID().toString());
    }
  }

}
