package com.certificadoranacional.ac.jpa.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_municipio",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"id_uf", "cd_municipio"}), @UniqueConstraint(columnNames = {"id_uf", "nm_municipio"})})
// @formatter:off
@AttributeOverrides({
  @AttributeOverride(name = "id", column = @Column(name = "id_municipio")),
  @AttributeOverride(name = "codigo", column = @Column(name = "cd_municipio", length = 10)),
  @AttributeOverride(name = "nome", column = @Column(name = "nm_municipio", length = 100))
})
//@formatter:on
public class Municipio extends AuxEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_uf", referencedColumnName = "id_uf", nullable = false)
  @NotNull
  private UF uf;

  @OneToMany(mappedBy = "municipio", fetch = FetchType.LAZY)
  private List<Posto> postoList;

  public Municipio() {
    super();
  }

  public Municipio(final String id) {
    super(id);
  }

  public UF getUf() {
    return this.uf;
  }

  public void setUf(final UF uf) {
    this.uf = uf;
  }

  public List<Posto> getPostoList() {
    return this.postoList;
  }

  public void setPostoList(final List<Posto> postoList) {
    this.postoList = postoList;
  }

}
