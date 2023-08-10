package com.certificadoranacional.ac.jpa.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_grupo_produto", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_tipo_produto", "cd_grupo_produto"})})
// @formatter:off
@AttributeOverrides({
  @AttributeOverride(name = "id", column = @Column(name = "id_grupo_produto")),
  @AttributeOverride(name = "codigo", column = @Column(name = "cd_grupo_produto", length = 20)),
  @AttributeOverride(name = "nome", column = @Column(name = "nm_grupo_produto", length = 100))
})
//@formatter:on
public class GrupoProduto extends AuxEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @Column(name = "nr_ordem", nullable = false)
  @NotNull
  private Integer ordem;

  @Column(name = "ds_grupo_produto", length = 4000, nullable = true)
  @Size(max = 4000)
  private String descricao;

  @Column(name = "ds_url_imagem", length = 4000, nullable = true)
  @Size(max = 4000)
  private String imagem;

  @Column(name = "st_grupo_produto", nullable = false)
  @NotNull
  private Boolean ativo;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_tipo_produto", referencedColumnName = "id_tipo_produto", nullable = false)
  @NotNull
  private TipoProduto tipo;

  public GrupoProduto() {
    super();
  }

  public GrupoProduto(final String id) {
    super(id);
  }

  public Integer getOrdem() {
    return this.ordem;
  }

  public void setOrdem(final Integer ordem) {
    this.ordem = ordem;
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

}
