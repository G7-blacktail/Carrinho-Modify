package com.certificadoranacional.ac.jpa.entity;

import java.math.BigDecimal;

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
@Table(name = "tb_produto", uniqueConstraints = {@UniqueConstraint(columnNames = {"cd_produto"})})
public class Produto extends AbstractEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  @Column(name = "id_produto", length = 100)
  private String id;

  @Column(name = "cd_produto", length = 50, nullable = false)
  @NotNull
  @Size(min = 1, max = 50)
  private String codigo;

  @Column(name = "cd_produto_serpro", length = 50)
  private String codigoSerpro;

  @Column(name = "nr_ordem", nullable = false)
  @NotNull
  private Integer ordem;

  @Column(name = "nm_produto", length = 200, nullable = false)
  @NotNull
  @Size(min = 1, max = 200)
  private String nome;

  @Column(name = "tx_produto", length = 4000, nullable = false)
  @NotNull
  @Size(min = 1, max = 4000)
  private String descricao;

  @Column(name = "ds_url_imagem", length = 4000, nullable = true)
  @Size(max = 4000)
  private String imagem;

  @Column(name = "nr_validade", nullable = true)
  private Integer validade;

  @Column(name = "vl_produto", nullable = false)
  @NotNull
  private BigDecimal valor;

  @Column(name = "vl_desconto", nullable = false)
  @NotNull
  private BigDecimal valorDesconto;

  @Column(name = "vl_referencia", nullable = false)
  @NotNull
  private BigDecimal valorReferencia;

  @Column(name = "st_produto", nullable = false)
  @NotNull
  private Boolean ativo;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_tipo_produto", referencedColumnName = "id_tipo_produto", nullable = false)
  @NotNull
  private TipoProduto tipo;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_grupo_produto", referencedColumnName = "id_grupo_produto", nullable = false)
  @NotNull
  private GrupoProduto grupo;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_subgrupo_produto", referencedColumnName = "id_subgrupo_produto", nullable = false)
  @NotNull
  private SubGrupoProduto subGrupo;

  public Produto() {
    super();
  }

  public Produto(final String id) {
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

  public String getCodigoSerpro() {
    return this.codigoSerpro;
  }

  public void setCodigoSerpro(final String codigoSerpro) {
    this.codigoSerpro = codigoSerpro;
  }

  public Integer getOrdem() {
    return this.ordem;
  }

  public void setOrdem(final Integer ordem) {
    this.ordem = ordem;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(final String nome) {
    this.nome = nome;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public void setDescricao(final String descricao) {
    this.descricao = descricao;
  }

  public String getImagem() {
    return this.imagem;
  }

  public void setImagem(final String imagem) {
    this.imagem = imagem;
  }

  public Integer getValidade() {
    return this.validade;
  }

  public void setValidade(final Integer validade) {
    this.validade = validade;
  }

  public BigDecimal getValor() {
    return this.valor;
  }

  public void setValor(final BigDecimal valor) {
    this.valor = valor;
  }

  public BigDecimal getValorDesconto() {
    return this.valorDesconto;
  }

  public void setValorDesconto(final BigDecimal valorDesconto) {
    this.valorDesconto = valorDesconto;
  }

  public BigDecimal getValorReferencia() {
    return this.valorReferencia;
  }

  public void setValorReferencia(final BigDecimal valorReferencia) {
    this.valorReferencia = valorReferencia;
  }

  public Boolean getAtivo() {
    return this.ativo;
  }

  public void setAtivo(final Boolean ativo) {
    this.ativo = ativo;
  }

  public TipoProduto getTipo() {
    return this.tipo;
  }

  public void setTipo(final TipoProduto tipo) {
    this.tipo = tipo;
  }

  public GrupoProduto getGrupo() {
    return this.grupo;
  }

  public void setGrupo(final GrupoProduto grupo) {
    this.grupo = grupo;
  }

  public SubGrupoProduto getSubGrupo() {
    return this.subGrupo;
  }

  public void setSubGrupo(final SubGrupoProduto subGrupo) {
    this.subGrupo = subGrupo;
  }

}
