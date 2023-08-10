package com.certificadoranacional.ac.jpa.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_convenio", uniqueConstraints = {@UniqueConstraint(columnNames = {"cd_convenio"})})
public class Convenio extends AbstractEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  @Column(name = "id_convenio", length = 100)
  private String id;

  @Column(name = "cd_convenio", length = 20, nullable = false)
  @NotNull
  @Size(min = 1, max = 20)
  private String codigo;

  @Column(name = "nm_convenio", length = 100, nullable = false)
  @NotNull
  @Size(min = 1, max = 100)
  private String nome;

  @Column(name = "vl_convenio", nullable = false)
  @NotNull
  private BigDecimal valor;

  @Column(name = "st_convenio", nullable = false)
  @NotNull
  private Boolean ativo;

  public Convenio() {
    super();
  }

  public Convenio(final String id) {
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

  public String getCodigo() {
    return this.codigo;
  }

  public void setCodigo(final String codigo) {
    this.codigo = codigo;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(final String nome) {
    this.nome = nome;
  }

  public BigDecimal getValor() {
    return this.valor;
  }

  public void setValor(final BigDecimal valor) {
    this.valor = valor;
  }

  public Boolean getAtivo() {
    return this.ativo;
  }

  public void setAtivo(final Boolean ativo) {
    this.ativo = ativo;
  }

}
