package com.certificadoranacional.ac.jpa.repository;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class JpqlRepositoryCustomImpl<E> implements JpqlRepositoryCustom<E> {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public List<E> findByJpql(final String jpaql, final Map<String, ? extends Object> params) {
    return this.doQuery(jpaql, params, null);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public Page<E> findByJpql(final String jpaql, final String countJpasql, final Map<String, ? extends Object> params, final Pageable pageable) {
    Number count = this.doCount(countJpasql, params);
    List<E> items = this.doQuery(jpaql, params, pageable);
    Page<E> result = new PageImpl<>(items, pageable, count.longValue());
    return result;
  }

  private Number doCount(final String jpaql, final Map<String, ? extends Object> params) {
    List<Number> list = this.doQuery(jpaql, params, null);
    if (list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  private <T> List<T> doQuery(final String jpaql, final Map<String, ? extends Object> params, final Pageable pageable) {
    Query query = this.entityManager.createQuery(jpaql);
    if ((params != null) && (!params.isEmpty())) {
      for (Entry<String, ? extends Object> entry : params.entrySet()) {
        query.setParameter(entry.getKey(), entry.getValue());
      }
    }
    if (pageable != null) {
      query.setMaxResults(pageable.getPageSize());
      query.setFirstResult((int) pageable.getOffset());
    }
    return query.getResultList();
  }

}
