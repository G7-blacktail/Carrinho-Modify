package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.certificadoranacional.ac.jpa.entity.PedidoProduto;

public interface PedidoProdutoJpaRepository extends JpaRepository<PedidoProduto, String> {

  Page<PedidoProduto> findByPedidoId(String id, Pageable pageable);

}
