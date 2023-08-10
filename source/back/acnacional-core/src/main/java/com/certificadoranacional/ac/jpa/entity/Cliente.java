package com.certificadoranacional.ac.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_cliente", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuario"})})
public class Cliente extends AbstractEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  @Column(name = "id_cliente", length = 100)
  private String id;

  @Column(name = "nm_cliente", length = 100, nullable = false)
  @NotNull
  @Size(min = 1, max = 100)
  private String nome;

  @Column(name = "nr_documento", length = 20)
  @Size(max = 20)
  private String documento;

  @Column(name = "ds_email", length = 200, nullable = false)
  @NotNull
  @Size(min = 1, max = 200)
  private String email;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
  @NotNull
  private Usuario usuario;

  public Cliente() {
    super();
  }

  public Cliente(final String id) {
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

  public String getDocumento() {
    return this.documento;
  }

  public void setDocumento(final String documento) {
    this.documento = documento;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public Usuario getUsuario() {
    return this.usuario;
  }

  public void setUsuario(final Usuario usuario) {
    this.usuario = usuario;
  }

}
