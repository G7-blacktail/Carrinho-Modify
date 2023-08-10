package com.certificadoranacional.ac.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_pre_cadastro")
public class PreCadastro extends AbstractEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  @Column(name = "id_pre_cadastro", length = 100)
  private String id;

  @Column(name = "tp_pre_cadastro")
  @NotNull
  private Integer tipo;

  // Comum
  @Column(name = "nm_responsavel", length = 100)
  @Size(max = 100)
  private String nomeResp;

  @Column(name = "dt_nascimento_responsavel", length = 10)
  @Size(max = 10)
  private String dataNascimentoResp;

  @Column(name = "nr_cpf_responsavel", length = 20)
  @Size(max = 20)
  private String cpfResp;

  @Column(name = "ds_email_responsavel", length = 100)
  @Size(max = 100)
  private String emailResp;

  @Column(name = "nr_rg_responsavel", length = 20)
  @Size(max = 20)
  private String rgResp;

  @Column(name = "ds_orgao_rg_responsavel", length = 20)
  @Size(max = 20)
  private String orgaoEmissorRgResp;

  @Column(name = "sg_uf_rg_responsavel", length = 2)
  @Size(max = 2)
  private String ufOrgaoEmissorRgResp;

  @Column(name = "nr_pis_responsavel", length = 100)
  @Size(max = 100)
  private String pisPasepResp;

  @Column(name = "nr_cep_responsavel", length = 10)
  @Size(max = 10)
  private String cepResp;

  @Column(name = "ds_logradouro_responsavel", length = 100)
  @Size(max = 100)
  private String logradouroResp;

  @Column(name = "nr_endereco_responsavel", length = 20)
  @Size(max = 20)
  private String numeroEnderecoResp;

  @Column(name = "ds_complemento_responsavel", length = 100)
  @Size(max = 100)
  private String complementoEnderecoResp;

  @Column(name = "ds_bairro_responsavel", length = 100)
  @Size(max = 100)
  private String bairroResp;

  @Column(name = "nm_municipio_responsavel", length = 100)
  @Size(max = 100)
  private String municipioResp;

  @Column(name = "sg_uf_responsavel", length = 2)
  @Size(max = 2)
  private String ufResp;

  @Column(name = "nr_telefone_responsavel", length = 20)
  @Size(max = 20)
  private String telefonePrincipal;

  @Column(name = "nr_tel_alt_responsavel", length = 20)
  @Size(max = 20)
  private String telefoneAlternativo;

  // PF;
  @Column(name = "nr_titulo_eleitor", length = 100)
  @Size(max = 100)
  private String tituloEleitor;

  @Column(name = "nr_zona_eleitor", length = 20)
  @Size(max = 20)
  private String zonaTituloEleitor;

  @Column(name = "nr_secao_eleitor", length = 20)
  @Size(max = 20)
  private String secaoTituloEleitor;

  @Column(name = "nm_municipio_eleitor", length = 100)
  @Size(max = 100)
  private String municipioTituloEleitor;

  @Column(name = "sg_uf_eleitor", length = 2)
  @Size(max = 2)
  private String ufTituloEleitor;

  @Column(name = "nr_inss_pf", length = 100)
  @Size(max = 100)
  private String inssCeiPf;

  // PJ;
  @Column(name = "nm_empresarial", length = 100)
  @Size(max = 100)
  private String nomeEmpresarial;

  @Column(name = "nr_cnpj", length = 20)
  @Size(max = 20)
  private String cnpj;

  @Column(name = "nr_inss_pj", length = 100)
  @Size(max = 100)
  private String inssCeiPj;

  @Column(name = "nm_municipio_empresa", length = 100)
  @Size(max = 100)
  private String municipioEmpresa;

  @Column(name = "sg_uf_empresa", length = 2)
  @Size(max = 2)
  private String ufEmpresa;

  @Column(name = "nm_representante_legal", length = 100)
  @Size(max = 100)
  private String nomeRepresentanteLegal;

  @Column(name = "nr_cpf_representante_legal", length = 20)
  @Size(max = 20)
  private String cpfRepresentanteLegal;

  @Column(name = "dt_nascimento_representante_legal", length = 10)
  @Size(max = 10)
  private String dataNascimentoRepresentanteLegal;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", nullable = false)
  private Cliente cliente;

  public PreCadastro() {
    super();
  }

  public PreCadastro(final String id) {
    super(id);
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public void setId(final String id) {
    this.id = id;
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

  public Cliente getCliente() {
    return this.cliente;
  }

  public void setCliente(final Cliente cliente) {
    this.cliente = cliente;
  }

}
