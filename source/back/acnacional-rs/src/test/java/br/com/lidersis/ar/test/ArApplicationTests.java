package br.com.lidersis.ar.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.certificadoranacional.ac.core.model.HorarioRepresentation;
import com.certificadoranacional.ac.core.service.HorarioService;
import com.certificadoranacional.ac.jpa.util.SpringRepositoryHelper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev-2")
public class ArApplicationTests {

  @Autowired
  private HorarioService horarioService;

  public ArApplicationTests() {
    super();
  }

  @Test
  public void testHorario() {
    Page<HorarioRepresentation> page = this.horarioService.list(SpringRepositoryHelper.ALL_PAGEABLE);
    List<HorarioRepresentation> list = page.getContent();
    for (HorarioRepresentation item : list) {
      System.out.println(item.getCodigo());
      System.out.println("  " + item.getCodigo());
      System.out.println("  " + item.getDescricao());
      System.out.println("  " + item.getInicio());
      System.out.println("  " + item.getFim());
    }
  }

}
