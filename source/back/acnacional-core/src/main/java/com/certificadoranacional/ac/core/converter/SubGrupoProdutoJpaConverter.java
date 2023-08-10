package com.certificadoranacional.ac.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.SubGrupoProdutoRepresentation;
import com.certificadoranacional.ac.jpa.entity.SubGrupoProduto;

@Component
public class SubGrupoProdutoJpaConverter extends AuxJpaConverter<SubGrupoProduto, SubGrupoProdutoRepresentation> {

  @Autowired
  private GrupoProdutoJpaConverter grupoProdutoJpaConverter;

  @Autowired
  private TipoProdutoJpaConverter tipoProdutoJpaConverter;

  public SubGrupoProdutoJpaConverter() {
    super();
  }

  @Override
  public SubGrupoProdutoRepresentation convert(SubGrupoProduto obj) {
    SubGrupoProdutoRepresentation rep = super.convert(obj);
    if (rep != null) {
      rep.setAtivo(obj.getAtivo());
      rep.setDescricao(obj.getDescricao());
      rep.setOrdem(obj.getOrdem());
      rep.setGrupo(this.grupoProdutoJpaConverter.convert(obj.getGrupo()));
      rep.setImagem(obj.getImagem());
      rep.setTipo(this.tipoProdutoJpaConverter.convert(obj.getGrupo().getTipo()));
    }
    return rep;
  }

  @Override
  public SubGrupoProduto convertBack(SubGrupoProdutoRepresentation rep) {
    SubGrupoProduto obj = super.convertBack(rep);
    if (obj != null) {
      obj.setAtivo(rep.getAtivo());
      obj.setDescricao(rep.getDescricao());
      obj.setOrdem(rep.getOrdem());
      obj.setGrupo(this.grupoProdutoJpaConverter.convertBack(rep.getGrupo()));
      obj.setImagem(rep.getImagem());
    }
    return obj;
  }

}
