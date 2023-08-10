package com.certificadoranacional.ac.core.service;

import com.certificadoranacional.ac.core.Log;
import com.certificadoranacional.ac.core.converter.HorarioPostoJpaConverter;
import com.certificadoranacional.ac.core.model.HorarioPostoRepresentation;
import com.certificadoranacional.ac.jpa.entity.HorarioPosto;
import com.certificadoranacional.ac.jpa.entity.Voucher;
import com.certificadoranacional.ac.jpa.repository.HorarioPostoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.VoucherJpaRepository;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HorarioPostoServiceImpl implements HorarioPostoService {

  @Autowired
  private HorarioPostoJpaRepository horarioPostoJpaRepository;

  @Autowired
  private HorarioPostoJpaConverter horarioPostoJpaConverter;

  @Autowired
  private VoucherJpaRepository voucherJpaRepository;

  public HorarioPostoServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public HorarioPostoRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    HorarioPosto obj = this.horarioPostoJpaRepository.findById(id).orElse(null);
    return this.horarioPostoJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public HorarioPostoRepresentation save(final HorarioPostoRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Hor]ario não pode ser nulo");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(rep.getId()), "ID não pode ser vazio");

    HorarioPosto entity = this.horarioPostoJpaRepository.findById(rep.getId()).orElse(null);
    Preconditions.checkState(entity != null, "Horário não encontrado");

    if (entity.getDisponivel().booleanValue()) {
      if (!Strings.isNullOrEmpty(rep.getEvento())) {
        entity.setDisponivel(Boolean.FALSE);
        entity.setEvento(rep.getEvento());
        entity.setTipo(HorarioPosto.TIPO_EVENTO);
        this.horarioPostoJpaRepository.save(entity);
      }
    } else {
      if (Strings.isNullOrEmpty(rep.getEvento())) {
        entity.setDisponivel(Boolean.TRUE);
        entity.setEvento(null);
        entity.setTipo(HorarioPosto.TIPO_DISPONIVEL);
        this.updateVoucher(entity);
        this.horarioPostoJpaRepository.save(entity);
      }
    }

    return this.horarioPostoJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Boolean delete(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    HorarioPosto obj = this.horarioPostoJpaRepository.findById(id).orElse(null);
    if (obj != null) {
      if (!obj.getDisponivel().booleanValue()) {
        obj.setDisponivel(Boolean.TRUE);
        obj.setEvento(null);
        this.updateVoucher(obj);
        this.horarioPostoJpaRepository.save(obj);
        return Boolean.TRUE;
      }
    }
    return Boolean.FALSE;
  }

  private void updateVoucher(final HorarioPosto entity) {
    Voucher tmp = this.voucherJpaRepository.findByHorarioPostoId(entity.getId());
    if (tmp != null) {
      tmp.setHorarioPosto(null);
      this.voucherJpaRepository.save(tmp);
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<HorarioPostoRepresentation> listByIdPostoData(final String idPosto, final String data, final Boolean status, final Pageable pageable) {
    String str = null;
    if (!Strings.isNullOrEmpty(data)) {
      if (data.length() == 10) {
        str = data.substring(6) + data.substring(3, 5) + data.substring(0, 2);
      } else {
        str = data;
      }
    }
    Page<HorarioPosto> page = this.horarioPostoJpaRepository.findByPostoIdAndDataTxBetweenOrderByDataTxAscHorarioInicioAsc(idPosto, str, str, pageable);
    if ((status != null) && (status.booleanValue())) {
      for (HorarioPosto item : page.getContent()) {
        if (item.getDisponivel().booleanValue()) {
          try {
            HorarioPostoServiceHelper.check(item);
          } catch (IllegalStateException e) {
            Log.getLog().debug(e.getMessage());
            item.setDisponivel(Boolean.FALSE);
            item.setEvento("Horário ultrapassado");
            item.setTipo(HorarioPosto.TIPO_ULTRAPASSADO);
            this.horarioPostoJpaRepository.save(item);
          }
        }
      }
    }
    return this.horarioPostoJpaConverter.convert(page);
  }

}
