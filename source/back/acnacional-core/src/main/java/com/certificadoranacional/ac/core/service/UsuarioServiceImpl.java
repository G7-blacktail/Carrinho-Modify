package com.certificadoranacional.ac.core.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.converter.UsuarioJpaConverter;
import com.certificadoranacional.ac.core.model.UsuarioRepresentation;
import com.certificadoranacional.ac.jpa.entity.Usuario;
import com.certificadoranacional.ac.jpa.repository.UsuarioJpaRepository;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

  public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  public static final String DOCUMENT_PATTERN = "^\\d{11}$";

  @Autowired
  @Lazy
  private UsuarioJpaRepository usuarioJpaRepository;

  @Autowired
  private UsuarioJpaConverter usuarioJpaConverter;

  public UsuarioServiceImpl() {
    super();
  }

  @Override
  public UsuarioRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Usuario usuario = this.usuarioJpaRepository.findById(id).orElse(null);
    if (usuario == null) {
      usuario = this.usuarioJpaRepository.findByEmail(id);
    }
    return this.usuarioJpaConverter.convert(usuario);
  }

  @Override
  public UsuarioRepresentation save(final UsuarioRepresentation rep) {
    this.validar(rep, false);

    Usuario usuario = this.usuarioJpaConverter.convertBack(rep);
    usuario.setAtivo(Boolean.TRUE);
    usuario.setEmail(usuario.getEmail().toLowerCase());
    usuario = this.usuarioJpaRepository.save(usuario);
    return this.usuarioJpaConverter.convert(usuario);
  }

  @Override
  public UsuarioRepresentation update(final UsuarioRepresentation rep) {
    this.validar(rep, true);

    Usuario tmp = this.usuarioJpaConverter.convertBack(rep);
    Usuario u = this.usuarioJpaRepository.findById(tmp.getId()).get();
    u.setAtivo(tmp.getAtivo());
    u.setNome(tmp.getNome());

    u = this.usuarioJpaRepository.save(u);
    return this.usuarioJpaConverter.convert(u);
  }

  @Override
  public void delete(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    Usuario u = this.usuarioJpaRepository.findById(id).orElse(null);
    Preconditions.checkState(u != null, "Usuário não encontrado");
    this.usuarioJpaRepository.delete(u);
  }

  @Override
  public Page<UsuarioRepresentation> list(final String nome, final Pageable pageable) {
    String str = Strings.nullToEmpty(nome);
    return this.usuarioJpaConverter.convert(this.usuarioJpaRepository.findByNomeContaining(str, SpringRepositoryHelper.toSort(pageable, "nome")));
  }

  private void validar(final UsuarioRepresentation usuario, final boolean atualizacao) {
    Preconditions.checkArgument(usuario != null, "Usuário não pode ser nulo");
    Preconditions.checkState(!Strings.isNullOrEmpty(usuario.getEmail()), "E-mail não pode ser vazio");
    Preconditions.checkState(!Strings.isNullOrEmpty(usuario.getNome()), "Nome não pode ser vazio");

    Preconditions.checkState(Pattern.matches(UsuarioServiceImpl.EMAIL_PATTERN, usuario.getEmail()), "E-mail Inválido");

    if (atualizacao) {
      Preconditions.checkState(usuario.getId() != null, "ID não pode ser nulo");
      Preconditions.checkState(usuario.getAtivo() != null, "Ativo não pode ser nulo");

      Usuario tmp1 = this.usuarioJpaRepository.findById(usuario.getId()).orElse(null);
      Preconditions.checkState(tmp1 != null, "Usuário não encontrado");

      Usuario tmp2 = this.usuarioJpaRepository.findByEmail(usuario.getEmail());
      if (tmp2 != null) {
        Preconditions.checkState(tmp2.getId().equals(usuario.getId()), "E-Mail já cadastrado");
      }

    } else {
      Usuario tmp1 = this.usuarioJpaRepository.findByEmail(usuario.getEmail());
      Preconditions.checkState(tmp1 == null, "E-Mail já cadastrado");
    }
  }

}
