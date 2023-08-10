package com.certificadoranacional.ac.jpa.entity;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import com.certificadoranacional.ac.core.Constants;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@MappedSuperclass
@EntityListeners(IdListener.class)
public abstract class AbstractEntity implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  public AbstractEntity() {
    super();
  }

  public AbstractEntity(final String id) {
    super();
    this.setId(id);
  }

  public abstract String getId();

  public abstract void setId(final String id);

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof AbstractEntity) {
      AbstractEntity other = (AbstractEntity) obj;
      return Objects.equal(this.getId(), other.getId());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(MoreObjects.firstNonNull(this.getId(), this));
  }
  
  @Override
  public String toString() {
    if (this.getId() != null) {
      return this.getId();
    }
    return super.toString();
  }
}
