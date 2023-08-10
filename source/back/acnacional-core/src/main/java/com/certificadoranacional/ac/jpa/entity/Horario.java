package com.certificadoranacional.ac.jpa.entity;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_horario", uniqueConstraints = {@UniqueConstraint(columnNames = {"cd_horario"})})
public class Horario extends AbstractEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  @Column(name = "id_horario", length = 100)
  private String id;

  @Column(name = "cd_horario", length = 10, nullable = false)
  @NotNull
  @Size(min = 1, max = 100)
  private String codigo;

  @Column(name = "ds_horario", length = 100, nullable = false)
  @NotNull
  @Size(min = 1, max = 100)
  private String descricao;

  @Column(name = "tm_inicio", nullable = false)
  // @Temporal(TemporalType.TIME)
  @NotNull
  private Time inicio;

  @Column(name = "tm_fim", nullable = false)
  // @Temporal(TemporalType.TIME)
  @NotNull
  private Time fim;

  public Horario() {
    super();
  }

  public Horario(final String id) {
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

  public String getDescricao() {
    return this.descricao;
  }

  public void setDescricao(final String descricao) {
    this.descricao = descricao;
  }

  public Time getInicio() {
    return this.inicio;
  }

  public void setInicio(final Time inicio) {
    this.inicio = inicio;
  }

  public Time getFim() {
    return this.fim;
  }

  public void setFim(final Time fim) {
    this.fim = fim;
  }

}
