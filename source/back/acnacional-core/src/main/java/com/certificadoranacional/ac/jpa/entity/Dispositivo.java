package com.certificadoranacional.ac.jpa.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_formato_entrega", uniqueConstraints = {@UniqueConstraint(columnNames = {"cd_formato_entrega"})})
// @formatter:off
@AttributeOverrides({
  @AttributeOverride(name = "id", column = @Column(name = "id_formato_entrega")),
  @AttributeOverride(name = "codigo", column = @Column(name = "cd_formato_entrega", length = 20)),
  @AttributeOverride(name = "nome", column = @Column(name = "nm_formato_entrega", length = 100))
})
//@formatter:on
public class Dispositivo extends AuxEntity {

  private static final long serialVersionUID = Constants.VERSION;

  public Dispositivo() {
    super();
  }

  public Dispositivo(final String id) {
    super(id);
  }
}
