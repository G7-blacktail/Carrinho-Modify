package com.certificadoranacional.ac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.Log;
import com.certificadoranacional.ac.jpa.entity.GrupoProduto;
import com.certificadoranacional.ac.jpa.entity.SubGrupoProduto;
import com.certificadoranacional.ac.jpa.entity.TipoProduto;
import com.certificadoranacional.ac.jpa.repository.GrupoProdutoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.SubGrupoProdutoJpaRepository;
import com.certificadoranacional.ac.jpa.repository.TipoProdutoJpaRepository;

@Component
public class CreateBaseObjectsListener implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private TipoProdutoJpaRepository tipoProdutoJpaRepository;

  @Autowired
  private GrupoProdutoJpaRepository grupoProdutoJpaRepository;

  @Autowired
  private SubGrupoProdutoJpaRepository subGrupoProdutoJpaRepository;

  public CreateBaseObjectsListener() {
    super();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    Log.getLog().info("CreateBaseObjectsListener.onApplicationEvent()");
    this.checkTipoProduto();
  }

  private void checkTipoProduto() {
    this.doCheckTipoProduto("CERTIFICADO", "Certificado", 1);
    this.doCheckTipoProduto("PERIFERICO", "Periférico", 2);
  }

  private void doCheckTipoProduto(final String codigo, final String nome, int ordem) {
    TipoProduto tipo = this.tipoProdutoJpaRepository.findByCodigo(codigo);
    if (tipo == null) {
      tipo = new TipoProduto();
      tipo.setAtivo(Boolean.TRUE);
      tipo.setCodigo(codigo);
      tipo.setNome(nome);
      tipo.setOrdem(Integer.valueOf(ordem));
      this.tipoProdutoJpaRepository.save(tipo);
      this.tipoProdutoJpaRepository.flush();
    }
    if (codigo.equals("CERTIFICADO")) {
      this.checkGrupoProdutoCertificado(tipo);
    }
    if (codigo.equals("PERIFERICO")) {
      this.checkGrupoProdutoPeriferico(tipo);
    }
  }

  private void checkGrupoProdutoCertificado(final TipoProduto tipo) {
    this.doCheckGrupoProduto(tipo, "ECPF", "e-CPF", 1);
    this.doCheckGrupoProduto(tipo, "ECNPJ", "e-CNPJ", 2);
    this.doCheckGrupoProduto(tipo, "NFE", "NFe", 3);
  }

  private void checkGrupoProdutoPeriferico(final TipoProduto tipo) {
    this.doCheckGrupoProduto(tipo, "CARTAO", "Cartão", 1);
    this.doCheckGrupoProduto(tipo, "LEITOR", "Leitor de Cartão", 2);
    this.doCheckGrupoProduto(tipo, "TOKEN", "Token", 3);
  }

  private void doCheckGrupoProduto(final TipoProduto tipo, final String codigo, final String nome, int ordem) {
    GrupoProduto grupo = this.grupoProdutoJpaRepository.findByTipoIdAndCodigo(tipo.getId(), codigo);
    if (grupo == null) {
      grupo = new GrupoProduto();
      grupo.setAtivo(Boolean.TRUE);
      grupo.setCodigo(codigo);
      grupo.setNome(nome);
      grupo.setOrdem(Integer.valueOf(ordem));
      grupo.setTipo(tipo);
      this.grupoProdutoJpaRepository.save(grupo);
      this.grupoProdutoJpaRepository.flush();
    }
    if (tipo.getCodigo().equals("CERTIFICADO")) {
      this.checkSubGrupoProdutoCertificado(grupo);
    }
    if (tipo.getCodigo().equals("PERIFERICO")) {
      this.checkSubGrupoProdutoPeriferico(grupo);
    }
  }

  private void checkSubGrupoProdutoCertificado(final GrupoProduto grupo) {
    this.doCheckSubGrupoProduto(grupo, "A1", "Certificado A1", 1);
    this.doCheckSubGrupoProduto(grupo, "A3CA", "Certificado A3 + Cartão", 2);
    this.doCheckSubGrupoProduto(grupo, "A3CALE", "Certificado A3 + Cartão + Leitora", 3);
    this.doCheckSubGrupoProduto(grupo, "A3TK", "Certificado A3 + Token USB", 4);
    this.doCheckSubGrupoProduto(grupo, "A3", "Certificado A3", 5);
    if (grupo.getCodigo().equals("ECNPJ")) {
      this.doCheckSubGrupoProduto(grupo, "A1MEI", "Certificado A1 (MEI)", 6);
    }
  }

  private void checkSubGrupoProdutoPeriferico(final GrupoProduto grupo) {
    this.doCheckSubGrupoProduto(grupo, "CA", "Cartão", 1);
    this.doCheckSubGrupoProduto(grupo, "LT", "Leitora de Cartão", 2);
    this.doCheckSubGrupoProduto(grupo, "TK", "Token", 3);
  }

  private void doCheckSubGrupoProduto(final GrupoProduto grupo, final String codigo, final String nome, int ordem) {
    SubGrupoProduto subGrupo = this.subGrupoProdutoJpaRepository.findByGrupoIdAndCodigo(grupo.getId(), codigo);
    if (subGrupo == null) {
      subGrupo = new SubGrupoProduto();
      subGrupo.setAtivo(Boolean.TRUE);
      subGrupo.setCodigo(codigo);
      subGrupo.setGrupo(grupo);
      subGrupo.setNome(nome);
      subGrupo.setOrdem(Integer.valueOf(ordem));
      this.subGrupoProdutoJpaRepository.save(subGrupo);
      this.subGrupoProdutoJpaRepository.flush();
    }
  }

}
