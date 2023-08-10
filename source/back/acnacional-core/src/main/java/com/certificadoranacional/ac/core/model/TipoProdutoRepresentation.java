package com.certificadoranacional.ac.core.model;

import com.certificadoranacional.ac.core.Constants;

public class TipoProdutoRepresentation extends AuxRepresentation {

  private static final long serialVersionUID = Constants.VERSION;

  private Integer ordem;

  private String descricao;

  private String imagem;

  private Boolean ativo;

  public TipoProdutoRepresentation() {
    super();
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
