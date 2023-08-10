package com.certificadoranacional.ac.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.certificadoranacional.ac.jpa.entity.Usuario;

public interface UsuarioJpaRepository extends JpaRepository<Usuario, String> {

  Usuario findByEmail(String email);

  Page<Usuario> findByNomeContaining(String nome, Pageable pageable);

}
