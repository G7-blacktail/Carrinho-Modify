package com.certificadoranacional.ac.core.model;

import java.io.Serializable;
import java.util.List;

import com.certificadoranacional.ac.core.Constants;

public class PedidoRepresentation implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private String id;

  private String codigo;

  private String data;

  private String hora;

  private String valor;

  private Integer situacao;

  private String codigoSolicitacaoPagamento;

  private String codigoTransacaoPagamento;

  private String urlSolicitacaoPagamento;

  private ClienteRepresentation cliente;

  private PostoRepresentation posto;

  private ConvenioRepresentation convenio;

  private List<PedidoProdutoRepresentation> produtoList;

  private List<VoucherRepresentation> voucherList;

  private String cep;

  public PedidoRepresentation() {
    super();
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getCodigo() {
    return this.codigo;
  }

  public void setCodigo(final String codigo) {
    this.codigo = codigo;
  }

  public String getData() {
    return this.data;
  }

  public void setData(final String data) {
    this.data = data;
  }

  public String getHora() {
    return this.hora;
  }

  public void setHora(final String hora) {
    this.hora = hora;
  }

  public String getValor() {
    return this.valor;
  }

  public void setValor(final String valor) {
    this.valor = valor;
  }

  public Integer getSituacao() {
    return this.situacao;
  }

  public void setSituacao(final Integer situacao) {
    this.situacao = situacao;
  }

  public String getCodigoSolicitacaoPagamento() {
    return this.codigoSolicitacaoPagamento;
  }

  public void setCodigoSolicitacaoPagamento(final String codigoSolicitacaoPagamento) {
    this.codigoSolicitacaoPagamento = codigoSolicitacaoPagamento;
  }

  public String getCodigoTransacaoPagamento() {
    return this.codigoTransacaoPagamento;
  }

  public void setCodigoTransacaoPagamento(final String codigoTransacaoPagamento) {
    this.codigoTransacaoPagamento = codigoTransacaoPagamento;
  }

  public String getUrlSolicitacaoPagamento() {
    return this.urlSolicitacaoPagamento;
  }

  public void setUrlSolicitacaoPagamento(final String urlSolicitacaoPagamento) {
    this.urlSolicitacaoPagamento = urlSolicitacaoPagamento;
  }

  public ClienteRepresentation getCliente() {
    return this.cliente;
  }

  public void setCliente(final ClienteRepresentation cliente) {
    this.cliente = cliente;
  }

  public PostoRepresentation getPosto() {
    return this.posto;
  }

  public void setPosto(final PostoRepresentation posto) {
    this.posto = posto;
  }

  public ConvenioRepresentation getConvenio() {
    return this.convenio;
  }

  public void setConvenio(final ConvenioRepresentation convenio) {
    this.convenio = convenio;
  }

  public List<PedidoProdutoRepresentation> getProdutoList() {
    return this.produtoList;
  }

  public void setProdutoList(final List<PedidoProdutoRepresentation> produtoList) {
    this.produtoList = produtoList;
  }

  public List<VoucherRepresentation> getVoucherList() {
    return this.voucherList;
  }

  public void setVoucherList(final List<VoucherRepresentation> voucherList) {
    this.voucherList = voucherList;
  }


    /**
     * @return String return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

}
