package com.certificadoranacional.ac.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.PreCadastroRepresentation;
import com.certificadoranacional.ac.jpa.entity.PreCadastro;

@Component
public class PreCadastroJpaConverter extends AbstractConverter<PreCadastro, PreCadastroRepresentation> {

  @Autowired
  private ClienteJpaConverter clienteJpaConverter;

  public PreCadastroJpaConverter() {
    super();
  }

  @Override
  public PreCadastroRepresentation convert(final PreCadastro obj) {
    if (obj == null) {
      return null;
    }
    PreCadastroRepresentation rep = new PreCadastroRepresentation();
    rep.setBairroResp(obj.getBairroResp());
    rep.setCepResp(obj.getCepResp());
    rep.setCliente(this.clienteJpaConverter.convert(obj.getCliente()));
    rep.setCnpj(obj.getCnpj());
    rep.setComplementoEnderecoResp(obj.getComplementoEnderecoResp());
    rep.setCpfRepresentanteLegal(obj.getCpfRepresentanteLegal());
    rep.setCpfResp(obj.getCpfResp());
    rep.setDataNascimentoRepresentanteLegal(obj.getDataNascimentoRepresentanteLegal());
    rep.setDataNascimentoResp(obj.getDataNascimentoResp());
    rep.setEmailResp(obj.getEmailResp());
    rep.setId(obj.getId());
    rep.setInssCeiPf(obj.getInssCeiPf());
    rep.setInssCeiPj(obj.getInssCeiPj());
    rep.setLogradouroResp(obj.getLogradouroResp());
    rep.setMunicipioEmpresa(obj.getMunicipioEmpresa());
    rep.setMunicipioResp(obj.getMunicipioResp());
    rep.setMunicipioTituloEleitor(obj.getMunicipioTituloEleitor());
    rep.setNomeEmpresarial(obj.getNomeEmpresarial());
    rep.setNomeRepresentanteLegal(obj.getNomeRepresentanteLegal());
    rep.setNomeResp(obj.getNomeResp());
    rep.setNumeroEnderecoResp(obj.getNumeroEnderecoResp());
    rep.setOrgaoEmissorRgResp(obj.getOrgaoEmissorRgResp());
    rep.setPisPasepResp(obj.getPisPasepResp());
    rep.setRgResp(obj.getRgResp());
    rep.setSecaoTituloEleitor(obj.getSecaoTituloEleitor());
    rep.setTelefoneAlternativo(obj.getTelefoneAlternativo());
    rep.setTelefonePrincipal(obj.getTelefonePrincipal());
    rep.setTipo(obj.getTipo());
    rep.setTituloEleitor(obj.getTituloEleitor());
    rep.setUfEmpresa(obj.getUfEmpresa());
    rep.setUfOrgaoEmissorRgResp(obj.getUfOrgaoEmissorRgResp());
    rep.setUfResp(obj.getUfResp());
    rep.setUfTituloEleitor(obj.getUfTituloEleitor());
    rep.setZonaTituloEleitor(obj.getZonaTituloEleitor());
    return rep;
  }

  @Override
  public PreCadastro convertBack(final PreCadastroRepresentation rep) {
    if (rep == null) {
      return null;
    }
    PreCadastro obj = new PreCadastro();
    obj.setBairroResp(rep.getBairroResp());
    obj.setCepResp(rep.getCepResp());
    obj.setCliente(this.clienteJpaConverter.convertBack(rep.getCliente()));
    obj.setCnpj(rep.getCnpj());
    obj.setComplementoEnderecoResp(rep.getComplementoEnderecoResp());
    obj.setCpfRepresentanteLegal(rep.getCpfRepresentanteLegal());
    obj.setCpfResp(rep.getCpfResp());
    obj.setDataNascimentoRepresentanteLegal(rep.getDataNascimentoRepresentanteLegal());
    obj.setDataNascimentoResp(rep.getDataNascimentoResp());
    obj.setEmailResp(rep.getEmailResp());
    obj.setId(rep.getId());
    obj.setInssCeiPf(rep.getInssCeiPf());
    obj.setInssCeiPj(rep.getInssCeiPj());
    obj.setLogradouroResp(rep.getLogradouroResp());
    obj.setMunicipioEmpresa(rep.getMunicipioEmpresa());
    obj.setMunicipioResp(rep.getMunicipioResp());
    obj.setMunicipioTituloEleitor(rep.getMunicipioTituloEleitor());
    obj.setNomeEmpresarial(rep.getNomeEmpresarial());
    obj.setNomeRepresentanteLegal(rep.getNomeRepresentanteLegal());
    obj.setNomeResp(rep.getNomeResp());
    obj.setNumeroEnderecoResp(rep.getNumeroEnderecoResp());
    obj.setOrgaoEmissorRgResp(rep.getOrgaoEmissorRgResp());
    obj.setPisPasepResp(rep.getPisPasepResp());
    obj.setRgResp(rep.getRgResp());
    obj.setSecaoTituloEleitor(rep.getSecaoTituloEleitor());
    obj.setTelefoneAlternativo(rep.getTelefoneAlternativo());
    obj.setTelefonePrincipal(rep.getTelefonePrincipal());
    obj.setTipo(rep.getTipo());
    obj.setTituloEleitor(rep.getTituloEleitor());
    obj.setUfEmpresa(rep.getUfEmpresa());
    obj.setUfOrgaoEmissorRgResp(rep.getUfOrgaoEmissorRgResp());
    obj.setUfResp(rep.getUfResp());
    obj.setUfTituloEleitor(rep.getUfTituloEleitor());
    obj.setZonaTituloEleitor(rep.getZonaTituloEleitor());
    return obj;
  }

}
