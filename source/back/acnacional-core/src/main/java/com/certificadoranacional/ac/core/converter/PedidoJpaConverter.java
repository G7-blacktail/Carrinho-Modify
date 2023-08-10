package com.certificadoranacional.ac.core.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.certificadoranacional.ac.core.model.PedidoRepresentation;
import com.certificadoranacional.ac.core.utils.DateUtils;
import com.certificadoranacional.ac.jpa.entity.Pedido;
import com.google.common.base.Strings;

@Component
public class PedidoJpaConverter extends AbstractConverter<Pedido, PedidoRepresentation> {

  @Autowired
  private ClienteJpaConverter clienteJpaConverter;

  @Autowired
  private PostoJpaConverter postoJpaConverter;

  @Autowired
  private ConvenioJpaConverter convenioJpaConverter;

  @Autowired
  private DecimalConverter decimalConverter;

  @Autowired
  private LocalDateConverter localDateConverter;

  @Autowired
  private LocalTimeConverter localTimeConverter;

  public PedidoJpaConverter() {
    super();
  }

  @Override
  public PedidoRepresentation convert(final Pedido obj) {
    if (obj == null) {
      return null;
    }
    PedidoRepresentation rep = new PedidoRepresentation();
    rep.setCliente(this.clienteJpaConverter.convert(obj.getCliente()));
    rep.setCodigo(obj.getCodigo());
    rep.setCodigoSolicitacaoPagamento(obj.getCodigoSolicitacaoPagamento());
    rep.setCodigoTransacaoPagamento(obj.getCodigoTransacaoPagamento());
    rep.setConvenio(this.convenioJpaConverter.convert(obj.getConvenio()));
    rep.setId(obj.getId());
    rep.setPosto(this.postoJpaConverter.convert(obj.getPosto()));
    rep.setSituacao(obj.getSituacao());
    rep.setUrlSolicitacaoPagamento(obj.getUrlSolicitacaoPagamento());
    rep.setValor(this.decimalConverter.convert(obj.getValor()));
    if (obj.getData() != null) {
      LocalDateTime localDateTime = DateUtils.toLocalDateTime(obj.getData());
      LocalDate localDate = localDateTime.toLocalDate();
      LocalTime localTime = localDateTime.toLocalTime();
      String data = this.localDateConverter.convert(localDate);
      String hora = this.localTimeConverter.convert(localTime);
      rep.setData(data);
      rep.setHora(hora);
    }
    return rep;
  }

  @Override
  public Pedido convertBack(final PedidoRepresentation rep) {
    if (rep == null) {
      return null;
    }
    Pedido obj = new Pedido();
    obj.setCliente(this.clienteJpaConverter.convertBack(rep.getCliente()));
    obj.setCodigo(rep.getCodigo());
    obj.setCodigoSolicitacaoPagamento(rep.getCodigoSolicitacaoPagamento());
    obj.setCodigoTransacaoPagamento(rep.getCodigoTransacaoPagamento());
    obj.setConvenio(this.convenioJpaConverter.convertBack(rep.getConvenio()));
    obj.setId(rep.getId());
    obj.setPosto(this.postoJpaConverter.convertBack(rep.getPosto()));
    obj.setSituacao(rep.getSituacao());
    obj.setUrlSolicitacaoPagamento(rep.getUrlSolicitacaoPagamento());
    obj.setValor(this.decimalConverter.convertBack(rep.getValor()));
    if ((!Strings.isNullOrEmpty(rep.getData())) && (!Strings.isNullOrEmpty(rep.getHora()))) {
      LocalDate localDate = this.localDateConverter.convertBack(rep.getData());
      LocalTime localTime = this.localTimeConverter.convertBack(rep.getHora());
      LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
      Date date = DateUtils.toDate(localDateTime);
      obj.setData(date);
    }
    return obj;
  }

}
