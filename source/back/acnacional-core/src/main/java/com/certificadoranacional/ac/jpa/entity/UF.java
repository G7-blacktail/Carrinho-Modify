package com.certificadoranacional.ac.jpa.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_uf", uniqueConstraints = {@UniqueConstraint(columnNames = {"cd_uf"}), @UniqueConstraint(columnNames = {"nm_uf"})})
// @formatter:off
@AttributeOverrides({
  @AttributeOverride(name = "id", column = @Column(name = "id_uf")),
  @AttributeOverride(name = "codigo", column = @Column(name = "cd_uf", length = 2)),
  @AttributeOverride(name = "nome", column = @Column(name = "nm_uf", length = 100))
})
//@formatter:on
public class UF extends AuxEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @OneToMany(mappedBy = "uf", fetch = FetchType.LAZY)
  private List<Posto> postoList;

  public UF() {
    super();
  }

  public UF(final String id) {
    super(id);
  }

  public List<Posto> getPostoList() {
    return this.postoList;
  }

  public void setPostoList(final List<Posto> postoList) {
    this.postoList = postoList;
  }

}
