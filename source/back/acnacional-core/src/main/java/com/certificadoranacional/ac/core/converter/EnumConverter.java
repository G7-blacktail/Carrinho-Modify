package com.certificadoranacional.ac.core.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.certificadoranacional.ac.core.model.EnumRepresentation;
import com.google.common.base.Strings;

@SuppressWarnings("rawtypes")
public abstract class EnumConverter<T extends Enum, D extends EnumRepresentation> extends AbstractConverter<T, D> {

  private Class<T> objClass;

  private Class<D> representationClass;

  public EnumConverter() {
    super();
    this.init();
  }

  @Override
  public D convert(final T obj) {
    if (obj == null) {
      return null;
    }
    D t = this.newInstance();
    t.setCodigo(obj.name());
    t.setId(Integer.toString(obj.ordinal()));
    t.setNome(obj.toString());
    return t;
  }

  @Override
  public T convertBack(final D obj) {
    if (obj == null) {
      return null;
    }
    T t = null;
    if (!Strings.isNullOrEmpty(obj.getId())) {
      int index = Integer.parseInt(obj.getId());
      T[] array = this.objClass.getEnumConstants();
      if (array.length >= index) {
        t = array[index];
      }
    } else if (!Strings.isNullOrEmpty(obj.getCodigo())) {
      String name = obj.getCodigo();
      T[] array = this.objClass.getEnumConstants();
      for (T item : array) {
        if (item.name().equals(name)) {
          t = item;
          break;
        }
      }
    }
    return t;
  }

  @SuppressWarnings("unchecked")
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
      this.objClass = (Class<T>) types[0];
      this.representationClass = (Class<D>) types[1];
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private D newInstance() {
    try {
      return this.representationClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

}
