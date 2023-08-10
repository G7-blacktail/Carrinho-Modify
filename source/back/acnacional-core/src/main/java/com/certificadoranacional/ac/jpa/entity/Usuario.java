package com.certificadoranacional.ac.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"ds_email"})})
public class Usuario extends AbstractEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  @Column(name = "id_usuario", length = 100)
  private String id;

  @Column(name = "ds_email", length = 100, nullable = false)
  @NotNull
  @Size(min = 1, max = 100)
  private String email;

  @Column(name = "nm_usuario", length = 100, nullable = false)
  @NotNull
  @Size(min = 1, max = 100)
  private String nome;

  @Column(name = "st_usuario", nullable = false)
  @NotNull
  private Boolean ativo;

  public Usuario() {
    super();
  }

  public Usuario(final String id) {
    super(id);
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public void setId(final String id) {
    this.id = id;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(final String nome) {
    this.nome = nome;
  }

  public Boolean getAtivo() {
    return this.ativo;
  }

  public void setAtivo(final Boolean ativo) {
    this.ativo = ativo;
  }

  @Override
  public String toString() {
    return this.getEmail();
  }

}
