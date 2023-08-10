package com.certificadoranacional.ac.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.ProdutoRepresentation;
import com.certificadoranacional.ac.jpa.entity.Produto;

@Component
public class ProdutoJpaConverter extends AbstractConverter<Produto, ProdutoRepresentation> {

  @Autowired
  private TipoProdutoJpaConverter tipoProdutoJpaConverter;

  @Autowired
  private GrupoProdutoJpaConverter grupoProdutoJpaConverter;
  
  @Autowired
  private SubGrupoProdutoJpaConverter subGrupoProdutoJpaConverter;

  @Autowired
  private DecimalConverter decimalConverter;

  public ProdutoJpaConverter() {
    super();
  }

  @Override
  public ProdutoRepresentation convert(final Produto obj) {
    if (obj == null) {
      return null;
    }
    ProdutoRepresentation rep = new ProdutoRepresentation();
    rep.setAtivo(obj.getAtivo());
    rep.setCodigo(obj.getCodigo());
    rep.setCodigoSerpro(obj.getCodigoSerpro());
    rep.setDescricao(obj.getDescricao());
    rep.setGrupo(this.grupoProdutoJpaConverter.convert(obj.getGrupo()));
    rep.setId(obj.getId());
    rep.setImagem(obj.getImagem());
    rep.setNome(obj.getNome());
    rep.setOrdem(obj.getOrdem());
    rep.setSubGrupo(this.subGrupoProdutoJpaConverter.convert(obj.getSubGrupo()));
    rep.setTipo(this.tipoProdutoJpaConverter.convert(obj.getTipo()));
    rep.setValidade(obj.getValidade());
    rep.setValor(this.decimalConverter.convert(obj.getValor()));
    rep.setValorDesconto(this.decimalConverter.convert(obj.getValorDesconto()));
    rep.setValorReferencia(this.decimalConverter.convert(obj.getValorReferencia()));
    return rep;
  }

  @Override
  public Produto convertBack(final ProdutoRepresentation rep) {
    if (rep == null) {
      return null;
    }
    Produto obj = new Produto();
    obj.setAtivo(rep.getAtivo());
    obj.setCodigo(rep.getCodigo());
    obj.setCodigoSerpro(rep.getCodigoSerpro());
    obj.setDescricao(rep.getDescricao());
    obj.setGrupo(this.grupoProdutoJpaConverter.convertBack(rep.getGrupo()));
    obj.setId(rep.getId());
    obj.setImagem(rep.getImagem());
    obj.setNome(rep.getNome());
    obj.setOrdem(rep.getOrdem());
    obj.setSubGrupo(this.subGrupoProdutoJpaConverter.convertBack(rep.getSubGrupo()));
    obj.setTipo(this.tipoProdutoJpaConverter.convertBack(rep.getTipo()));
    obj.setValidade(rep.getValidade());
    obj.setValor(this.decimalConverter.convertBack(rep.getValor()));
    obj.setValorDesconto(this.decimalConverter.convertBack(rep.getValorDesconto()));
    obj.setValorReferencia(this.decimalConverter.convertBack(rep.getValorReferencia()));
    return obj;
  }

}
