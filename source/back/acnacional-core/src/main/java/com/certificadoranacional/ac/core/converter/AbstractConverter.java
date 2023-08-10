package com.certificadoranacional.ac.core.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.certificadoranacional.ac.core.Log;

public abstract class AbstractConverter<S, T> implements GenericConverter {

  private final Class<S> classOfS;

  private final Class<T> classOfT;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public AbstractConverter() {
    super();
    Type typeA = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    Type typeB = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    this.classOfS = (Class) typeA;
    this.classOfT = (Class) typeB;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object convert(final Object source, final TypeDescriptor sourceType, final TypeDescriptor targetType) {
    if (this.classOfS.equals(sourceType.getType())) {
      return this.convert((S) source);
    }
    return this.convertBack((T) source);
  }

  public List<T> convert(final List<S> objs) {
    if (objs == null) {
      return null;
    }
    List<T> list = new ArrayList<>();
    for (S obj : objs) {
      list.add(this.convert(obj));
    }
    return list;
  }

  public Page<T> convert(final Page<S> page) {
    if (page == null) {
      return null;
    }
    List<T> content = this.convert(page.getContent());
    int number = page.getNumber();
    int size = page.getSize();
    long total = page.getTotalElements();
    PageImpl<T> newPage = new PageImpl<>(content, PageRequest.of(number, size), total);
    return newPage;
  }

  public abstract T convert(S obj);

  public List<S> convertBack(final List<T> objs) {
    if (objs == null) {
      return null;
    }
    List<S> list = new ArrayList<>();
    for (T obj : objs) {
      list.add(this.convertBack(obj));
    }
    return list;
  }

  public S convertBack(T target) {
    Log.getLog().debug("Unsupported convert back from " + target);
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<ConvertiblePair> getConvertibleTypes() {
    Set<ConvertiblePair> convertiblePairs = new HashSet<>();
    convertiblePairs.add(new ConvertiblePair(this.classOfS, this.classOfT));
    convertiblePairs.add(new ConvertiblePair(this.classOfT, this.classOfS));
    return convertiblePairs;
  }

}
