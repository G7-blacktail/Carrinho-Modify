package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.certificadoranacional.ac.jpa.entity.Voucher;

public interface VoucherJpaRepository extends JpaRepository<Voucher, String> {

  Voucher findByCodigo(String codigo);

  Voucher findByHorarioPostoId(String id);

  Page<Voucher> findByClienteId(String idCliente, Pageable pageable);
  
  Page<Voucher> findByClienteIdAndAtivo(String idCliente, Boolean ativo, Pageable pageable);

  Page<Voucher> findByPedidoIdOrderByCodigoAsc(String idCliente, Pageable pageable);

  Page<Voucher> findByHorarioPostoDataTx(String data, Pageable pageable);

}
