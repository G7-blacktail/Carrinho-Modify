package com.certificadoranacional.ac.jpa.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JpqlRepositoryCustom<E> {

  List<E> findByJpql(String jpaql, Map<String, ? extends Object> params);

  Page<E> findByJpql(String jpaql, String countJpasql, Map<String, ? extends Object> params, Pageable pageable);

}
