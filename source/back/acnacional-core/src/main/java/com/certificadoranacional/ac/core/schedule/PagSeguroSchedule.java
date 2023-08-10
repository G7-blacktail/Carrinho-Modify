package com.certificadoranacional.ac.core.schedule;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.Log;
import com.certificadoranacional.ac.core.service.PagSeguroService;
import com.certificadoranacional.ac.core.utils.DateUtils;
import com.certificadoranacional.ac.jpa.entity.Pedido;
import com.certificadoranacional.ac.jpa.repository.PedidoJpaRepository;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;

import br.com.uol.pagseguro.api.exception.PagSeguroInternalServerException;
import br.com.uol.pagseguro.api.http.HttpResponse;

@Component
@ConditionalOnProperty(name = "certificadoranacional.ac.schedule.enabled", havingValue = "true")
public class PagSeguroSchedule {

  @Autowired
  private PedidoJpaRepository pedidoJpaRepository;

  @Autowired
  private PagSeguroService pagSeguroService;

  public PagSeguroSchedule() {
    super();
  }

  // @Scheduled(initialDelay = 60000, fixedDelay = 300000)
  @Scheduled(initialDelay = 15000, fixedDelay = 300000)
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public void execute() {
    this.doCheck();
    this.doCheckOld();
  }

  private void doCheck() {
    Log.getLog().info("Verificando os pagamentos no PagSeguro");
    Page<Pedido> page = this.pedidoJpaRepository.findBySituacao(Pedido.SITUACAO_AGUARDANDO_PAGAMENTO, SpringRepositoryHelper.ALL_PAGEABLE);
    List<Pedido> list = page.getContent();
    for (Pedido item : list) {
      try {
        Log.getLog().info("Verificando o pedido " + item.getCodigo());
        this.pagSeguroService.checkReference(item.getCodigo());
      } catch (PagSeguroInternalServerException e) {
        HttpResponse response = e.getServerResponse();
        String str = response.asString();
        Log.getLog().warn("PagSeguro Error: " + str);
      } catch (Exception e) {
        Log.getLog().warn(e.getMessage(), e);
        // Log.getLog().debug(e.getMessage(), e);
      }
    }
  }

  private void doCheckOld() {
    Log.getLog().info("Cancelando os pedidos antigos");
    Date data = DateUtils.toDate(LocalDateTime.now().minusMonths(3));
    Page<Pedido> page = this.pedidoJpaRepository.findBySituacaoAndDataLessThan(Pedido.SITUACAO_AGUARDANDO_PAGAMENTO, data, SpringRepositoryHelper.ALL_PAGEABLE);
    List<Pedido> list = page.getContent();
    for (Pedido item : list) {
      try {
        Log.getLog().info("Verificando o pedido " + item.getCodigo());
        item.setSituacao(Pedido.SITUACAO_CANCELADO);
        this.pedidoJpaRepository.save(item);
      } catch (Exception e) {
        Log.getLog().warn(e.getMessage(), e);
        // Log.getLog().debug(e.getMessage(), e);
      }
    }
  }

}
