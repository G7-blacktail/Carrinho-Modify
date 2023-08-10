package com.certificadoranacional.ac.core.service;

public interface NotificacaoEmailService {

  void enviarEmailAguardandoPagamento(String id);
  
  void enviarEmailConfirmacaoPagamento(String id);
  
  void enviarEmailAgendamentoVoucher(String id);

}
