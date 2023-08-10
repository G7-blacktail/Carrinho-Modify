package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.certificadoranacional.ac.jpa.entity.Cliente;

public interface ClienteJpaRepository extends JpaRepository<Cliente, String> {

  Cliente findByUsuarioId(String idUsuario);

  Page<Cliente> findByNomeContaining(String nome, Pageable pageable);

}
