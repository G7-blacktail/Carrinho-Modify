package com.certificadoranacional.ac.jpa.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_tipo_produto", uniqueConstraints = {@UniqueConstraint(columnNames = {"cd_tipo_produto"})})
// @formatter:off
@AttributeOverrides({
  @AttributeOverride(name = "id", column = @Column(name = "id_tipo_produto")),
  @AttributeOverride(name = "codigo", column = @Column(name = "cd_tipo_produto", length = 20)),
  @AttributeOverride(name = "nome", column = @Column(name = "nm_tipo_produto", length = 100))
})
//@formatter:on
public class TipoProduto extends AuxEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @Column(name = "nr_ordem", nullable = false)
  @NotNull
  private Integer ordem;

  @Column(name = "ds_tipo_produto", length = 4000, nullable = true)
  @Size(max = 4000)
  private String descricao;

  @Column(name = "ds_url_imagem", length = 4000, nullable = true)
  @Size(max = 4000)
  private String imagem;

  @Column(name = "st_tipo_produto", nullable = false)
  @NotNull
  private Boolean ativo;

  public TipoProduto() {
    super();
  }

  public TipoProduto(final String id) {
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

}
