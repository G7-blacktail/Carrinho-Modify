package com.certificadoranacional.ac.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.PostoRepresentation;
import com.certificadoranacional.ac.jpa.entity.Posto;

@Component
public class PostoJpaConverter extends AbstractConverter<Posto, PostoRepresentation> {

  @Autowired
  private MunicipioJpaConverter municipioJpaConverter;

  @Autowired
  private UfJpaConverter ufJpaConverter;

  public PostoJpaConverter() {
    super();
  }

  @Override
  public PostoRepresentation convert(final Posto obj) {
    if (obj == null) {
      return null;
    }
    PostoRepresentation rep = new PostoRepresentation();
    rep.setAtivo(obj.getAtivo());
    rep.setBairro(obj.getBairro());
    rep.setCep(obj.getCep());
    rep.setCodigo(obj.getCodigo());
    rep.setComplemento(obj.getComplemento());
    rep.setEmail(obj.getEmail());
    rep.setEndereco(obj.getEndereco());
    rep.setId(obj.getId());
    rep.setMapa(obj.getMapa());
    rep.setMunicipio(this.municipioJpaConverter.convert(obj.getMunicipio()));
    rep.setNome(obj.getNome());
    rep.setNumero(obj.getNumero());
    rep.setResponsavel(obj.getResponsavel());
    rep.setTelefone(obj.getTelefone());
    rep.setUf(this.ufJpaConverter.convert(obj.getUf()));
    return rep;
  }

  @Override
  public Posto convertBack(final PostoRepresentation rep) {
    if (rep == null) {
      return null;
    }
    Posto obj = new Posto();
    obj.setAtivo(rep.getAtivo());
    obj.setBairro(rep.getBairro());
    obj.setCep(rep.getCep());
    obj.setCodigo(rep.getCodigo());
    obj.setComplemento(rep.getComplemento());
    obj.setEmail(rep.getEmail());
    obj.setEndereco(rep.getEndereco());
    obj.setId(rep.getId());
    obj.setMapa(rep.getMapa());
    obj.setMunicipio(this.municipioJpaConverter.convertBack(rep.getMunicipio()));
    obj.setNome(rep.getNome());
    obj.setNumero(rep.getNumero());
    obj.setResponsavel(rep.getResponsavel());
    obj.setTelefone(rep.getTelefone());
    obj.setUf(this.ufJpaConverter.convertBack(rep.getUf()));
    return obj;
  }

}
