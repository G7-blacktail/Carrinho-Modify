package com.certificadoranacional.ac.jpa.util;

import java.util.Collections;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.google.common.base.Strings;

public abstract class SpringRepositoryHelper {

  public static final Sort ID_SORT = Sort.by(Direction.ASC, "id");

  public static final Map<String, Direction> ID_SORT_MAP = Collections.singletonMap("id", Direction.ASC);

  public static final Pageable ONE_PAGEABLE = PageRequest.of(0, 1);

  public static final Pageable ALL_PAGEABLE = PageRequest.of(0, Integer.MAX_VALUE);

  private SpringRepositoryHelper() {
    //
  }

  public static String getLikeValue(final String str) {
    if (Strings.isNullOrEmpty(str)) {
      return "%";
    }
    return "%" + str.trim() + "%";
  }

  public static Pageable toSort(final Pageable pageable, String... properties) {
    if (pageable == null) {
      return null;
    }
    if ((pageable.getSort() != null) && (pageable.getSort().isSorted())) {
      return pageable;
    }
    return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(properties));
  }

  public static Pageable toSort(final Pageable pageable, Direction direction, String... properties) {
    if (pageable == null) {
      return null;
    }
    if ((pageable.getSort() != null) && (pageable.getSort().isSorted())) {
      return pageable;
    }
    return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, properties));
  }

}
