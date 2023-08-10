package com.certificadoranacional.ac.core.model;

import java.io.Serializable;

import com.certificadoranacional.ac.core.Constants;

public class PreCadastroRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String id;

  private String idVoucher;

  private Integer tipo;

  private String nomeResp;

  private String dataNascimentoResp;

  private String cpfResp;

  private String emailResp;

  private String rgResp;

  private String orgaoEmissorRgResp;

  private String ufOrgaoEmissorRgResp;

  private String pisPasepResp;

  private String cepResp;

  private String logradouroResp;

  private String numeroEnderecoResp;

  private String complementoEnderecoResp;

  private String bairroResp;

  private String municipioResp;

  private String ufResp;

  private String telefonePrincipal;

  private String telefoneAlternativo;

  private String tituloEleitor;

  private String zonaTituloEleitor;

  private String secaoTituloEleitor;

  private String municipioTituloEleitor;

  private String ufTituloEleitor;

  private String inssCeiPf;

  private String nomeEmpresarial;

  private String cnpj;

  private String inssCeiPj;

  private String municipioEmpresa;

  private String ufEmpresa;

  private String nomeRepresentanteLegal;

  private String cpfRepresentanteLegal;

  private String dataNascimentoRepresentanteLegal;

  private ClienteRepresentation cliente;

  public PreCadastroRepresentation() {
    super();
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getIdVoucher() {
    return this.idVoucher;
  }

  public void setIdVoucher(final String idVoucher) {
    this.idVoucher = idVoucher;
  }

  public Integer getTipo() {
    return this.tipo;
  }

  public void setTipo(final Integer tipo) {
    this.tipo = tipo;
  }

  public String getNomeResp() {
    return this.nomeResp;
  }

  public void setNomeResp(final String nomeResp) {
    this.nomeResp = nomeResp;
  }

  public String getDataNascimentoResp() {
    return this.dataNascimentoResp;
  }

  public void setDataNascimentoResp(final String dataNascimentoResp) {
    this.dataNascimentoResp = dataNascimentoResp;
  }

  public String getCpfResp() {
    return this.cpfResp;
  }

  public void setCpfResp(final String cpfResp) {
    this.cpfResp = cpfResp;
  }

  public String getEmailResp() {
    return this.emailResp;
  }

  public void setEmailResp(final String emailResp) {
    this.emailResp = emailResp;
  }

  public String getRgResp() {
    return this.rgResp;
  }

  public void setRgResp(final String rgResp) {
    this.rgResp = rgResp;
  }

  public String getOrgaoEmissorRgResp() {
    return this.orgaoEmissorRgResp;
  }

  public void setOrgaoEmissorRgResp(final String orgaoEmissorRgResp) {
    this.orgaoEmissorRgResp = orgaoEmissorRgResp;
  }

  public String getUfOrgaoEmissorRgResp() {
    return this.ufOrgaoEmissorRgResp;
  }

  public void setUfOrgaoEmissorRgResp(final String ufOrgaoEmissorRgResp) {
    this.ufOrgaoEmissorRgResp = ufOrgaoEmissorRgResp;
  }

  public String getPisPasepResp() {
    return this.pisPasepResp;
  }

  public void setPisPasepResp(final String pisPasepResp) {
    this.pisPasepResp = pisPasepResp;
  }

  public String getCepResp() {
    return this.cepResp;
  }

  public void setCepResp(final String cepResp) {
    this.cepResp = cepResp;
  }

  public String getLogradouroResp() {
    return this.logradouroResp;
  }

  public void setLogradouroResp(final String logradouroResp) {
    this.logradouroResp = logradouroResp;
  }

  public String getNumeroEnderecoResp() {
    return this.numeroEnderecoResp;
  }

  public void setNumeroEnderecoResp(final String numeroEnderecoResp) {
    this.numeroEnderecoResp = numeroEnderecoResp;
  }

  public String getComplementoEnderecoResp() {
    return this.complementoEnderecoResp;
  }

  public void setComplementoEnderecoResp(final String complementoEnderecoResp) {
    this.complementoEnderecoResp = complementoEnderecoResp;
  }

  public String getBairroResp() {
    return this.bairroResp;
  }

  public void setBairroResp(final String bairroResp) {
    this.bairroResp = bairroResp;
  }

  public String getMunicipioResp() {
    return this.municipioResp;
  }

  public void setMunicipioResp(final String municipioResp) {
    this.municipioResp = municipioResp;
  }

  public String getUfResp() {
    return this.ufResp;
  }

  public void setUfResp(final String ufResp) {
    this.ufResp = ufResp;
  }

  public String getTelefonePrincipal() {
    return this.telefonePrincipal;
  }

  public void setTelefonePrincipal(final String telefonePrincipal) {
    this.telefonePrincipal = telefonePrincipal;
  }

  public String getTelefoneAlternativo() {
    return this.telefoneAlternativo;
  }

  public void setTelefoneAlternativo(final String telefoneAlternativo) {
    this.telefoneAlternativo = telefoneAlternativo;
  }

  public String getTituloEleitor() {
    return this.tituloEleitor;
  }

  public void setTituloEleitor(final String tituloEleitor) {
    this.tituloEleitor = tituloEleitor;
  }

  public String getZonaTituloEleitor() {
    return this.zonaTituloEleitor;
  }

  public void setZonaTituloEleitor(final String zonaTituloEleitor) {
    this.zonaTituloEleitor = zonaTituloEleitor;
  }

  public String getSecaoTituloEleitor() {
    return this.secaoTituloEleitor;
  }

  public void setSecaoTituloEleitor(final String secaoTituloEleitor) {
    this.secaoTituloEleitor = secaoTituloEleitor;
  }

  public String getMunicipioTituloEleitor() {
    return this.municipioTituloEleitor;
  }

  public void setMunicipioTituloEleitor(final String municipioTituloEleitor) {
    this.municipioTituloEleitor = municipioTituloEleitor;
  }

  public String getUfTituloEleitor() {
    return this.ufTituloEleitor;
  }

  public void setUfTituloEleitor(final String ufTituloEleitor) {
    this.ufTituloEleitor = ufTituloEleitor;
  }

  public String getInssCeiPf() {
    return this.inssCeiPf;
  }

  public void setInssCeiPf(final String inssCeiPf) {
    this.inssCeiPf = inssCeiPf;
  }

  public String getNomeEmpresarial() {
    return this.nomeEmpresarial;
  }

  public void setNomeEmpresarial(final String nomeEmpresarial) {
    this.nomeEmpresarial = nomeEmpresarial;
  }

  public String getCnpj() {
    return this.cnpj;
  }

  public void setCnpj(final String cnpj) {
    this.cnpj = cnpj;
  }

  public String getInssCeiPj() {
    return this.inssCeiPj;
  }

  public void setInssCeiPj(final String inssCeiPj) {
    this.inssCeiPj = inssCeiPj;
  }

  public String getMunicipioEmpresa() {
    return this.municipioEmpresa;
  }

  public void setMunicipioEmpresa(final String municipioEmpresa) {
    this.municipioEmpresa = municipioEmpresa;
  }

  public String getUfEmpresa() {
    return this.ufEmpresa;
  }

  public void setUfEmpresa(final String ufEmpresa) {
    this.ufEmpresa = ufEmpresa;
  }

  public String getNomeRepresentanteLegal() {
    return this.nomeRepresentanteLegal;
  }

  public void setNomeRepresentanteLegal(final String nomeRepresentanteLegal) {
    this.nomeRepresentanteLegal = nomeRepresentanteLegal;
  }

  public String getCpfRepresentanteLegal() {
    return this.cpfRepresentanteLegal;
  }

  public void setCpfRepresentanteLegal(final String cpfRepresentanteLegal) {
    this.cpfRepresentanteLegal = cpfRepresentanteLegal;
  }

  public String getDataNascimentoRepresentanteLegal() {
    return this.dataNascimentoRepresentanteLegal;
  }

  public void setDataNascimentoRepresentanteLegal(final String dataNascimentoRepresentanteLegal) {
    this.dataNascimentoRepresentanteLegal = dataNascimentoRepresentanteLegal;
  }

  public ClienteRepresentation getCliente() {
    return this.cliente;
  }

  public void setCliente(final ClienteRepresentation cliente) {
    this.cliente = cliente;
  }

}
