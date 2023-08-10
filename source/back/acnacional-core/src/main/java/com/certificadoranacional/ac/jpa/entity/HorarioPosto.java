package com.certificadoranacional.ac.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_horario_posto", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_horario", "id_posto", "dt_evento_tx"})})
public class HorarioPosto extends AbstractEntity {

  public static final Integer TIPO_ULTRAPASSADO = Integer.valueOf(99);
  
  public static final Integer TIPO_DISPONIVEL = Integer.valueOf(0);

  public static final Integer TIPO_VOUCHER = Integer.valueOf(1);

  public static final Integer TIPO_EVENTO = Integer.valueOf(2);

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  @Column(name = "id_horario_posto", length = 100)
  private String id;

  @Column(name = "dt_evento", nullable = true)
  @Temporal(TemporalType.DATE)
  private Date data;

  @Column(name = "dt_evento_tx", nullable = true, length = 10)
  private String dataTx;

  @Column(name = "ds_evento", length = 1000, nullable = true)
  @Size(max = 1000)
  private String evento;

  @Column(name = "tp_horario", nullable = false)
  @NotNull
  private Integer tipo;

  @Column(name = "st_horario", nullable = false)
  @NotNull
  private Boolean disponivel;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_horario", referencedColumnName = "id_horario", nullable = false)
  @NotNull
  private Horario horario;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_posto", referencedColumnName = "id_posto", nullable = false)
  @NotNull
  private Posto posto;

  public HorarioPosto() {
    super();
  }

  public HorarioPosto(final String id) {
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

  public Date getData() {
    return this.data;
  }

  public void setData(final Date data) {
    this.data = data;
  }

  public String getDataTx() {
    return this.dataTx;
  }

  public void setDataTx(final String dataTx) {
    this.dataTx = dataTx;
  }

  public String getEvento() {
    return this.evento;
  }

  public void setEvento(final String evento) {
    this.evento = evento;
  }

  public Integer getTipo() {
    return this.tipo;
  }

  public void setTipo(final Integer tipo) {
    this.tipo = tipo;
  }

  public Boolean getDisponivel() {
    return this.disponivel;
  }

  public void setDisponivel(final Boolean disponivel) {
    this.disponivel = disponivel;
  }

  public Horario getHorario() {
    return this.horario;
  }

  public void setHorario(final Horario horario) {
    this.horario = horario;
  }

  public Posto getPosto() {
    return this.posto;
  }

  public void setPosto(final Posto posto) {
    this.posto = posto;
  }

}
