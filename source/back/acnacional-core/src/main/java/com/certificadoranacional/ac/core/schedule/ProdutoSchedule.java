package com.certificadoranacional.ac.core.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certificadoranacional.ac.core.Log;

@Component
@ConditionalOnProperty(name = "certificadoranacional.ac.schedule.enabled", havingValue = "true")
public class ProdutoSchedule {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public ProdutoSchedule() {
    super();
  }

  @Scheduled(initialDelay = 60000, fixedDelay = 60000)
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public void execute() {
    // @formatter:off
    Log.getLog().info("Atualizando os tipos, grupos e subgrupos inativos ");
    this.jdbcTemplate.update("update tb_tipo_produto t set t.st_tipo_produto = 0");
    this.jdbcTemplate.update("update tb_tipo_produto t set t.st_tipo_produto = 1 where exists (select 1 from tb_produto p where p.id_tipo_produto = t.id_tipo_produto)");
    
    this.jdbcTemplate.update("update tb_grupo_produto t set t.st_grupo_produto = 0");
    this.jdbcTemplate.update("update tb_grupo_produto t set t.st_grupo_produto = 1 where exists (select 1 from tb_produto p where p.id_grupo_produto = t.id_grupo_produto)");
    
    this.jdbcTemplate.update("update tb_subgrupo_produto t set t.st_subgrupo_produto = 0");
    this.jdbcTemplate.update("update tb_subgrupo_produto t set t.st_subgrupo_produto = 1 where exists (select 1 from tb_produto p where p.id_subgrupo_produto = t.id_subgrupo_produto)");
    // @formatter:on
  }

}
