package com.certificadoranacional.ac.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.converter.PreCadastroJpaConverter;
import com.certificadoranacional.ac.core.model.PreCadastroRepresentation;
import com.certificadoranacional.ac.jpa.entity.Cliente;
import com.certificadoranacional.ac.jpa.entity.PreCadastro;
import com.certificadoranacional.ac.jpa.entity.Voucher;
import com.certificadoranacional.ac.jpa.repository.PreCadastroJpaRepository;
import com.certificadoranacional.ac.jpa.repository.VoucherJpaRepository;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

@Service
public class PreCadastroServiceImpl implements PreCadastroService {

  @Autowired
  private PreCadastroJpaRepository preCadastroJpaRepository;

  @Autowired
  private VoucherJpaRepository voucherJpaRepository;

  @Autowired
  private PreCadastroJpaConverter preCadastroJpaConverter;

  @Autowired
  private SessionService sessionService;

  @Autowired
  private ClienteService clienteService;

  public PreCadastroServiceImpl() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PreCadastroRepresentation get(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    PreCadastro obj = this.preCadastroJpaRepository.findById(id).orElse(null);
    return this.preCadastroJpaConverter.convert(obj);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PreCadastroRepresentation save(final PreCadastroRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Pré cadastro não pode ser nulo");

    String idUsuario = this.sessionService.getIdUsuario();
    Cliente cliente = this.clienteService.getClienteByIdUsuario(idUsuario);

    PreCadastro entity = this.preCadastroJpaConverter.convertBack(rep);
    entity.setCliente(cliente);
    entity = this.preCadastroJpaRepository.save(entity);
    this.preCadastroJpaRepository.flush();

    if (!Strings.isNullOrEmpty(rep.getIdVoucher())) {
      Voucher voucher = this.voucherJpaRepository.findById(rep.getIdVoucher()).orElse(null);
      if (voucher != null) {
        voucher.setPreCadastro(entity);
        this.voucherJpaRepository.save(voucher);
        this.voucherJpaRepository.flush();
      }
    }

    return this.preCadastroJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public PreCadastroRepresentation update(final PreCadastroRepresentation rep) {
    Preconditions.checkArgument(rep != null, "Pré cadastro não pode ser nulo");

    String idUsuario = this.sessionService.getIdUsuario();
    Cliente cliente = this.clienteService.getClienteByIdUsuario(idUsuario);

    PreCadastro tmp = this.preCadastroJpaConverter.convertBack(rep);
    PreCadastro entity = this.preCadastroJpaRepository.findById(rep.getId()).orElse(null);
    Preconditions.checkState(entity != null, "Pré cadastro não encontrado");

    if (!this.sessionService.isAdministrador()) {
      Preconditions.checkState(entity.getCliente().getId().equals(cliente.getId()), "Pré cadastro não pode ser editado por seu usuário");
    }

    entity.setBairroResp(tmp.getBairroResp());
    entity.setCepResp(tmp.getCepResp());
    entity.setCnpj(tmp.getCnpj());
    entity.setComplementoEnderecoResp(tmp.getComplementoEnderecoResp());
    entity.setCpfRepresentanteLegal(tmp.getCpfRepresentanteLegal());
    entity.setCpfResp(tmp.getCpfResp());
    entity.setDataNascimentoRepresentanteLegal(tmp.getDataNascimentoRepresentanteLegal());
    entity.setDataNascimentoResp(tmp.getDataNascimentoResp());
    entity.setEmailResp(tmp.getEmailResp());
    entity.setId(tmp.getId());
    entity.setInssCeiPf(tmp.getInssCeiPf());
    entity.setInssCeiPj(tmp.getInssCeiPj());
    entity.setLogradouroResp(tmp.getLogradouroResp());
    entity.setMunicipioEmpresa(tmp.getMunicipioEmpresa());
    entity.setMunicipioResp(tmp.getMunicipioResp());
    entity.setMunicipioTituloEleitor(tmp.getMunicipioTituloEleitor());
    entity.setNomeEmpresarial(tmp.getNomeEmpresarial());
    entity.setNomeRepresentanteLegal(tmp.getNomeRepresentanteLegal());
    entity.setNomeResp(tmp.getNomeResp());
    entity.setNumeroEnderecoResp(tmp.getNumeroEnderecoResp());
    entity.setOrgaoEmissorRgResp(tmp.getOrgaoEmissorRgResp());
    entity.setPisPasepResp(tmp.getPisPasepResp());
    entity.setRgResp(tmp.getRgResp());
    entity.setSecaoTituloEleitor(tmp.getSecaoTituloEleitor());
    entity.setTelefoneAlternativo(tmp.getTelefoneAlternativo());
    entity.setTelefonePrincipal(tmp.getTelefonePrincipal());
    entity.setTituloEleitor(tmp.getTituloEleitor());
    entity.setUfEmpresa(tmp.getUfEmpresa());
    entity.setUfOrgaoEmissorRgResp(tmp.getUfOrgaoEmissorRgResp());
    entity.setUfResp(tmp.getUfResp());
    entity.setUfTituloEleitor(tmp.getUfTituloEleitor());
    entity.setZonaTituloEleitor(tmp.getZonaTituloEleitor());

    entity = this.preCadastroJpaRepository.save(entity);
    return this.preCadastroJpaConverter.convert(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Boolean delete(final String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "ID não pode ser vazio");
    PreCadastro obj = this.preCadastroJpaRepository.findById(id).orElse(null);
    if (obj != null) {
      this.preCadastroJpaRepository.delete(obj);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Page<PreCadastroRepresentation> listByCliente(final String idCliente, final Integer tipo, final Pageable pageable) {
    String idToSearch = idCliente;
    if ((Strings.isNullOrEmpty(idCliente)) || (!this.sessionService.isAdministrador())) {
      String idUsuario = this.sessionService.getIdUsuario();
      Cliente cliente = this.clienteService.getClienteByIdUsuario(idUsuario);
      idToSearch = cliente.getId();
    }

    Pageable pageableToSearch = SpringRepositoryHelper.toSort(pageable, "nomeEmpresarial", "nomeResp");

    Page<PreCadastro> page = null;
    if (tipo != null) {
      page = this.preCadastroJpaRepository.findByClienteIdAndTipo(idToSearch, tipo, pageableToSearch);
    } else {
      page = this.preCadastroJpaRepository.findByClienteId(idToSearch, pageableToSearch);
    }
    return this.preCadastroJpaConverter.convert(page);
  }

}
