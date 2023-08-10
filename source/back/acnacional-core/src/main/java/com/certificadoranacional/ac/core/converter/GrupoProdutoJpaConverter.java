package com.certificadoranacional.ac.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.GrupoProdutoRepresentation;
import com.certificadoranacional.ac.jpa.entity.GrupoProduto;

@Component
public class GrupoProdutoJpaConverter extends AuxJpaConverter<GrupoProduto, GrupoProdutoRepresentation> {

  @Autowired
  private TipoProdutoJpaConverter tipoProdutoJpaConverter;

  public GrupoProdutoJpaConverter() {
    super();
  }

  @Override
  public GrupoProdutoRepresentation convert(GrupoProduto obj) {
    GrupoProdutoRepresentation rep = super.convert(obj);
    if (rep != null) {
      rep.setAtivo(obj.getAtivo());
      rep.setDescricao(obj.getDescricao());
      rep.setOrdem(obj.getOrdem());
      rep.setImagem(obj.getImagem());
      rep.setTipo(this.tipoProdutoJpaConverter.convert(obj.getTipo()));
    }
    return rep;
  }

  @Override
  public GrupoProduto convertBack(GrupoProdutoRepresentation rep) {
    GrupoProduto obj = super.convertBack(rep);
    if (obj != null) {
      obj.setAtivo(rep.getAtivo());
      obj.setDescricao(rep.getDescricao());
      obj.setOrdem(rep.getOrdem());
      obj.setImagem(rep.getImagem());
      obj.setTipo(this.tipoProdutoJpaConverter.convertBack(rep.getTipo()));
    }
    return obj;
  }

}
