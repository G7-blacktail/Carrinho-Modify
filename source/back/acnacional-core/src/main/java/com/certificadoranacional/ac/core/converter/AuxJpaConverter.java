package com.certificadoranacional.ac.core.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.certificadoranacional.ac.core.model.AuxRepresentation;
import com.certificadoranacional.ac.jpa.entity.AuxEntity;

public abstract class AuxJpaConverter<I extends AuxEntity, O extends AuxRepresentation> extends AbstractConverter<I, O> {

  private Class<I> entityClass;

  private Class<O> representationClass;

  public AuxJpaConverter() {
    super();
    this.init();
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void init() {
    try {
      Type type = this.getClass().getGenericSuperclass();
      ParameterizedType parameterizedType = null;
      if (type instanceof ParameterizedType) {
        parameterizedType = (ParameterizedType) type;
      } else {
        Class clazz = (Class) type;
        parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
      }
      Type[] types = parameterizedType.getActualTypeArguments();
      this.entityClass = (Class<I>) types[0];
      this.representationClass = (Class<O>) types[1];
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public O convert(final I obj) {
    if (obj == null) {
      return null;
    }
    O rep = this.newRepresenation();
    rep.setCodigo(obj.getCodigo());
    rep.setId(obj.getId());
    rep.setNome(obj.getNome());
    return rep;
  }

  @Override
  public I convertBack(final O rep) {
    if (rep == null) {
      return null;
    }
    I obj = this.newEntity();
    obj.setCodigo(rep.getCodigo());
    obj.setId(rep.getId());
    obj.setNome(rep.getNome());
    return obj;
  }

  protected I newEntity() {
    try {
      return this.entityClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  protected O newRepresenation() {
    try {
      return this.representationClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

}
