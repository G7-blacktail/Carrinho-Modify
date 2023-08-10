package com.certificadoranacional.ac.jpa.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.certificadoranacional.ac.core.Constants;

@MappedSuperclass
public class AuxEntity extends AbstractEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  private String id;

  @NotNull
  private String codigo;

  @NotNull
  private String nome;

  public AuxEntity() {
    super();
  }

  public AuxEntity(final String id) {
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

}
