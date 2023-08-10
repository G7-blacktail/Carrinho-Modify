package com.certificadoranacional.ac.core.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.converter.HorarioPostoJpaConverter;
import com.certificadoranacional.ac.core.converter.PostoJpaConverter;
import com.certificadoranacional.ac.core.converter.VoucherJpaConverter;
import com.certificadoranacional.ac.core.model.AgendaRepresentation;
import com.certificadoranacional.ac.core.model.DiaAgendaRepresentation;
import com.certificadoranacional.ac.core.model.ItemAgendaRepresentation;
import com.certificadoranacional.ac.core.utils.DateUtils;
import com.certificadoranacional.ac.jpa.entity.HorarioPosto;
import com.certificadoranacional.ac.jpa.entity.Posto;
import com.certificadoranacional.ac.jpa.entity.Voucher;
import com.certificadoranacional.ac.jpa.repository.HorarioPostoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.PostoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.VoucherJpaRepository;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@Service
public class AgendaServiceImpl implements AgendaService {

  @Autowired
  private HorarioPostoJpaRepository horarioPostoJpaRepository;

  @Autowired
  private HorarioPostoJpaConverter horarioPostoJpaConverter;

  @Autowired
  private VoucherJpaRepository voucherJpaRepository;

  @Autowired
  private VoucherJpaConverter voucherJpaConverter;

  @Autowired
  private PostoJpaRepository postoJpaRepository;

  @Autowired
  private PostoJpaConverter postoJpaConverter;

  private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private DateTimeFormatter dayNameFormatter = DateTimeFormatter.ofPattern("EEEE");

  private DateTimeFormatter monthNameFormatter = DateTimeFormatter.ofPattern("MMMM");

  public AgendaServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public AgendaRepresentation get(final String idPosto, final Integer mes, final Integer ano) {
    try {
      Preconditions.checkArgument(!Strings.isNullOrEmpty(idPosto), "ID não pode ser vazio");
      LocalDate now = LocalDate.now();
      Integer mesReal = MoreObjects.firstNonNull(mes, Integer.valueOf(now.getMonthValue()));
      Integer anoReal = MoreObjects.firstNonNull(ano, Integer.valueOf(now.getYear()));

      LocalDate localDate = LocalDate.of(anoReal.intValue(), mesReal.intValue(), 1);

      String mesStr = String.format("%02d", mesReal);
      String anoStr = String.format("%04d", anoReal);

      Posto posto = this.postoJpaRepository.findById(idPosto).orElse(null);
      Preconditions.checkState(posto != null, "Posto não encontrado");

      String start = anoStr + mesStr + "01";
      String end = anoStr + mesStr + "31";

      Pageable pageable = SpringRepositoryHelper.ALL_PAGEABLE;

      Page<HorarioPosto> page = this.horarioPostoJpaRepository.findByPostoIdAndDataTxBetweenOrderByDataTxAscHorarioInicioAsc(idPosto, start, end, pageable);
      List<HorarioPosto> list = page.getContent();

      AgendaRepresentation representation = new AgendaRepresentation();
      representation.setAno(anoStr);
      representation.setMes(mesStr);
      representation.setMesStr(this.monthNameFormatter.format(localDate));
      representation.setPosto(this.postoJpaConverter.convert(posto));
      representation.setDiaList(this.toList(list, localDate));
      return representation;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private List<DiaAgendaRepresentation> toList(final List<HorarioPosto> list, final LocalDate date) {
    LocalDate startDate = LocalDate.from(date);
    int month = startDate.getMonthValue();
    Map<Integer, DiaAgendaRepresentation> map = new LinkedHashMap<>();
    while (startDate.getMonthValue() == month) {
      String diaStr = String.format("%02d", Integer.valueOf(startDate.getDayOfMonth()));
      String dataStr = this.dateTimeFormatter.format(startDate);
      DiaAgendaRepresentation dia = new DiaAgendaRepresentation();
      dia.setData(dataStr);
      dia.setDia(diaStr);
      dia.setDiaStr(this.dayNameFormatter.format(startDate));
      dia.setItemList(new LinkedList<>());
      map.put(Integer.valueOf(startDate.getDayOfMonth()), dia);
      startDate = startDate.plusDays(1);
    }
    for (HorarioPosto item : list) {
      if ((!item.getDisponivel().booleanValue()) && (!item.getTipo().equals(HorarioPosto.TIPO_ULTRAPASSADO))) {
        LocalDate localDate = DateUtils.toLocalDate(item.getData());
        DiaAgendaRepresentation dia = map.get(Integer.valueOf(localDate.getDayOfMonth()));
        ItemAgendaRepresentation representation = new ItemAgendaRepresentation();
        representation.setHorarioPosto(this.horarioPostoJpaConverter.convert(item));
        
        if (item.getTipo().equals(HorarioPosto.TIPO_VOUCHER)) {
          Voucher voucher = this.voucherJpaRepository.findByHorarioPostoId(item.getId());
          if (voucher != null) {
            representation.setVoucher(this.voucherJpaConverter.convert(voucher));
          }
        }

        representation.setNumero(Integer.valueOf(dia.getItemList().size() + 1));
        dia.getItemList().add(representation);
      }
    }

    return Lists.newLinkedList(map.values());
  }

}
