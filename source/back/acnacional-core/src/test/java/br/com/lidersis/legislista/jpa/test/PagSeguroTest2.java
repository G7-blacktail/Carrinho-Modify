package br.com.lidersis.legislista.jpa.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.DataList;
import br.com.uol.pagseguro.api.common.domain.TransactionStatus;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.HttpClient;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import br.com.uol.pagseguro.api.transaction.search.TransactionSummary;
import br.com.uol.pagseguro.api.utils.logging.LoggerFactory;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;

public class PagSeguroTest2 {

  private String email = "administrativo@lidersis.com.br";

  private String token = "04AE699E8D294398A32F28C2D21679D1";

  public PagSeguroTest2() {
    super();
  }

  private PagSeguro getPagSeguro() {
    LoggerFactory loggerFactory = new SimpleLoggerFactory();
    HttpClient httpClient = new JSEHttpClient();
    Credential credential = Credential.sellerCredential(this.email, this.token);
    PagSeguroEnv pagSeguroEnv = PagSeguroEnv.PRODUCTION;
    PagSeguro pagSeguro = PagSeguro.instance(loggerFactory, httpClient, credential, pagSeguroEnv);
    return pagSeguro;
  }

  @Test
  public void test1() throws Exception {
    PagSeguro pagSeguro = this.getPagSeguro();
    List<String> ids = Lists.newArrayList("PAJCPIMG", "EFXMDKUG", "PIPEYUQF", "GPBYEQKS", "FQZAFLFG", "OTEJAHJV", "UPWKNBIR", "XGCVCPZG", "HVQTTTOH", "EQHCIFMM", "FUVXETBU", "GSWOXOGH", "VRFCPEYB", "LLXVAKDM", "VSBQEKKI", "PXXXGQFI",
        "ZTKSTZWL", "BRSMMHOI", "OYHMAYQW", "ZKLMXTQT", "WJAQDTGG", "ERJLQCLN", "FXYHIGQZ", "YLJMLEVM", "VPPBTVMR", "GZZKGCXW", "RNQYEVWG", "AUXYUVSL", "RGRHTYKX", "MWLZCVXM", "ESHGLFCG", "QFHLAZZP", "RIWLIRBL", "WLHOGAVT", "GIHFJFWE",
        "KFKXLUWW", "RORSFLPY", "AFAAEHMH", "NAHJCLOW", "HRWGLEJY", "LDJGDJKI", "KUADHXQR", "DLZBDZSE", "FHPAIJLS", "VRZQUWSO", "OAPRUHCA", "TJSCSHZP", "MGYCTXPI", "ARSIJJBX", "QCWWACFY");

    List<String> done = new ArrayList<>();
    for (String id : ids) {
      try {
        DataList<? extends TransactionSummary> dataList = pagSeguro.transactions().search().byReference(id);
        for (TransactionSummary ts : dataList.getData()) {
          TransactionDetail transaction = ts.getDetail();
          if (transaction != null) {
            int status = transaction.getStatus().getStatusId();
            String reference = transaction.getReference();
            if ((status == TransactionStatus.Status.APPROVED.ordinal()) || (status == TransactionStatus.Status.AVAILABLE.ordinal())) {
              System.out.println("Pago: " + reference);
              done.add(reference);
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    System.out.println(Joiner.on(",").join(done));
  }

}
