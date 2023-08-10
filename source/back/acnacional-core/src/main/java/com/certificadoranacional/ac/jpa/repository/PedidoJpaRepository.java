package com.certificadoranacional.ac.jpa.repository;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.certificadoranacional.ac.jpa.entity.Pedido;

public interface PedidoJpaRepository extends JpaRepository<Pedido, String> {

  Pedido findByCodigo(String codigo);

  Page<Pedido> findByValorGreaterThan(BigDecimal v, Pageable pageable);

  Page<Pedido> findByClienteIdAndValorGreaterThan(String id, BigDecimal v, Pageable pageable);

  Page<Pedido> findBySituacao(Integer situacao, Pageable pageable);

  Page<Pedido> findBySituacaoAndDataLessThan(Integer situacao, Date data, Pageable pageable);

  Page<Pedido> findByClienteIdAndSituacao(String id, Integer situacao, Pageable pageable);

}
