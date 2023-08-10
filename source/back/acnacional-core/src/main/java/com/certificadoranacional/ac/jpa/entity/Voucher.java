package com.certificadoranacional.ac.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.certificadoranacional.ac.core.Constants;

@Entity
@Table(name = "tb_voucher", uniqueConstraints = {@UniqueConstraint(columnNames = {"cd_voucher"})})
public class Voucher extends AbstractEntity {

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  @Column(name = "id_voucher", length = 100)
  private String id;

  @Column(name = "cd_voucher", length = 50, nullable = false)
  @NotNull
  @Size(min = 1, max = 50)
  private String codigo;

  @Column(name = "st_voucher", nullable = false)
  @NotNull
  private Boolean ativo;

  @Column(name = "dt_confirmacao", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date dataConfirmacao;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", nullable = false)
  @NotNull
  private Cliente cliente;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_produto", referencedColumnName = "id_produto", nullable = false)
  @NotNull
  private Produto produto;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "id_pedido", referencedColumnName = "id_pedido", nullable = true)
  private Pedido pedido;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "id_pedido_produto", referencedColumnName = "id_pedido_produto", nullable = true)
  private PedidoProduto pedidoProduto;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "id_horario_posto", referencedColumnName = "id_horario_posto", nullable = true)
  private HorarioPosto horarioPosto;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "id_pre_cadastro", referencedColumnName = "id_pre_cadastro", nullable = true)
  private PreCadastro preCadastro;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "id_usuario_confirmacao", referencedColumnName = "id_usuario", nullable = true)
  private Usuario usuarioConfirmacao;

  public Voucher() {
    super();
  }

  public Voucher(final String id) {
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

  public String getCodigo() {
    return this.codigo;
  }

  public void setCodigo(final String codigo) {
    this.codigo = codigo;
  }

  public Boolean getAtivo() {
    return this.ativo;
  }

  public void setAtivo(final Boolean ativo) {
    this.ativo = ativo;
  }

  public Date getDataConfirmacao() {
    return this.dataConfirmacao;
  }

  public void setDataConfirmacao(final Date dataConfirmacao) {
    this.dataConfirmacao = dataConfirmacao;
  }

  public Cliente getCliente() {
    return this.cliente;
  }

  public void setCliente(final Cliente cliente) {
    this.cliente = cliente;
  }

  public Produto getProduto() {
    return this.produto;
  }

  public void setProduto(final Produto produto) {
    this.produto = produto;
  }

  public Pedido getPedido() {
    return this.pedido;
  }

  public void setPedido(final Pedido pedido) {
    this.pedido = pedido;
  }

  public PedidoProduto getPedidoProduto() {
    return this.pedidoProduto;
  }

  public void setPedidoProduto(final PedidoProduto pedidoProduto) {
    this.pedidoProduto = pedidoProduto;
  }

  public HorarioPosto getHorarioPosto() {
    return this.horarioPosto;
  }

  public void setHorarioPosto(final HorarioPosto horarioPosto) {
    this.horarioPosto = horarioPosto;
  }

  public PreCadastro getPreCadastro() {
    return this.preCadastro;
  }

  public void setPreCadastro(final PreCadastro preCadastro) {
    this.preCadastro = preCadastro;
  }

  public Usuario getUsuarioConfirmacao() {
    return this.usuarioConfirmacao;
  }

  public void setUsuarioConfirmacao(final Usuario usuarioConfirmacao) {
    this.usuarioConfirmacao = usuarioConfirmacao;
  }

}
