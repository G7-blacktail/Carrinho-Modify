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
@Table(name = "tb_posto", uniqueConstraints = {@UniqueConstraint(columnNames = {"cd_posto"})})
public class Posto extends AbstractEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  @Column(name = "id_posto", length = 100)
  private String id;

  @Column(name = "cd_posto", length = 50, nullable = false)
  @NotNull
  @Size(min = 1, max = 50)
  private String codigo;

  @Column(name = "nm_posto", length = 200, nullable = false)
  @NotNull
  @Size(min = 1, max = 200)
  private String nome;

  @Column(name = "nm_responsavel", length = 200, nullable = false)
  @NotNull
  @Size(min = 1, max = 200)
  private String responsavel;

  @Column(name = "ds_email", length = 200, nullable = false)
  @NotNull
  @Size(min = 1, max = 200)
  private String email;

  @Column(name = "ds_endereco", length = 1000, nullable = false)
  @NotNull
  @Size(min = 1, max = 1000)
  private String endereco;

  @Column(name = "ds_complemento", length = 1000, nullable = true)
  @Size(max = 1000)
  private String complemento;

  @Column(name = "nr_endereco", length = 100, nullable = true)
  @Size(max = 100)
  private String numero;

  @Column(name = "ds_bairro", length = 100, nullable = true)
  @Size(max = 100)
  private String bairro;

  @Column(name = "nr_cep", length = 100, nullable = true)
  @Size(max = 100)
  private String cep;

  @Column(name = "ds_telefone", length = 100, nullable = false)
  @NotNull
  @Size(min = 1, max = 100)
  private String telefone;

  @Column(name = "ds_url_mapa", length = 1000, nullable = true)
  @Size(max = 1000)
  private String mapa;

  @Column(name = "st_posto", nullable = false)
  @NotNull
  private Boolean ativo;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_uf", referencedColumnName = "id_uf", nullable = false)
  @NotNull
  private UF uf;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_municipio", referencedColumnName = "id_municipio", nullable = false)
  @NotNull
  private Municipio municipio;

  public Posto() {
    super();
  }

  public Posto(final String id) {
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

  public String getResponsavel() {
    return this.responsavel;
  }

  public void setResponsavel(final String responsavel) {
    this.responsavel = responsavel;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getEndereco() {
    return this.endereco;
  }

  public void setEndereco(final String endereco) {
    this.endereco = endereco;
  }

  public String getComplemento() {
    return this.complemento;
  }

  public void setComplemento(final String complemento) {
    this.complemento = complemento;
  }

  public String getNumero() {
    return this.numero;
  }

  public void setNumero(final String numero) {
    this.numero = numero;
  }

  public String getBairro() {
    return this.bairro;
  }

  public void setBairro(final String bairro) {
    this.bairro = bairro;
  }

  public String getCep() {
    return this.cep;
  }

  public void setCep(final String cep) {
    this.cep = cep;
  }

  public String getTelefone() {
    return this.telefone;
  }

  public void setTelefone(final String telefone) {
    this.telefone = telefone;
  }

  public String getMapa() {
    return this.mapa;
  }

  public void setMapa(final String mapa) {
    this.mapa = mapa;
  }

  public Boolean getAtivo() {
    return this.ativo;
  }

  public void setAtivo(final Boolean ativo) {
    this.ativo = ativo;
  }

  public UF getUf() {
    return this.uf;
  }

  public void setUf(final UF uf) {
    this.uf = uf;
  }

  public Municipio getMunicipio() {
    return this.municipio;
  }

  public void setMunicipio(final Municipio municipio) {
    this.municipio = municipio;
  }

}
