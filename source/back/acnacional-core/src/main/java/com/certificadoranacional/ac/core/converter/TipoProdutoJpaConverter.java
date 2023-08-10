package com.certificadoranacional.ac.core.converter;

import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.TipoProdutoRepresentation;
import com.certificadoranacional.ac.jpa.entity.TipoProduto;

@Component
public class TipoProdutoJpaConverter extends AuxJpaConverter<TipoProduto, TipoProdutoRepresentation> {

  public TipoProdutoJpaConverter() {
    super();
  }

  @Override
  public TipoProdutoRepresentation convert(TipoProduto obj) {
    TipoProdutoRepresentation rep = super.convert(obj);
    if (rep != null) {
      rep.setAtivo(obj.getAtivo());
      rep.setDescricao(obj.getDescricao());
      rep.setOrdem(obj.getOrdem());
      rep.setImagem(obj.getImagem());
    }
    return rep;
  }

  @Override
  public TipoProduto convertBack(TipoProdutoRepresentation rep) {
    TipoProduto obj = super.convertBack(rep);
    if (obj != null) {
      obj.setAtivo(rep.getAtivo());
      obj.setDescricao(rep.getDescricao());
      obj.setOrdem(rep.getOrdem());
      obj.setImagem(rep.getImagem());
    }
    return obj;
  }

}
