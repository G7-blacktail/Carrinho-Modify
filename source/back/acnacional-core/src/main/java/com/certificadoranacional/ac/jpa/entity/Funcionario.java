package com.certificadoranacional.ac.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_funcionario")
public class Funcionario extends AbstractEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  @Column(name = "id_funcionario", length = 100)
  private String id;

  @Column(name = "nm_cliente", length = 100, nullable = false)
  @NotNull
  @Size(min = 1, max = 100)
  private String nome;

  @Column(name = "tp_funcionario", nullable = false)
  @NotNull
  private Integer tipo;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_posto", referencedColumnName = "id_posto", nullable = false)
  @NotNull
  private Posto posto;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
  @NotNull
  private Usuario usuario;

  public Funcionario() {
    super();
  }

  public Funcionario(final String id) {
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

  public String getNome() {
    return this.nome;
  }

  public void setNome(final String nome) {
    this.nome = nome;
  }

  public Integer getTipo() {
    return this.tipo;
  }

  public void setTipo(final Integer tipo) {
    this.tipo = tipo;
  }

  public Posto getPosto() {
    return this.posto;
  }

  public void setPosto(final Posto posto) {
    this.posto = posto;
  }

  public Usuario getUsuario() {
    return this.usuario;
  }

  public void setUsuario(final Usuario usuario) {
    this.usuario = usuario;
  }

}
