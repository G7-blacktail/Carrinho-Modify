package br.com.lidersis.legislista.jpa.test;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import br.com.uol.pagseguro.api.Endpoints;
import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.checkout.CheckoutRegistrationBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.AcceptedPaymentMethodsBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ConfigBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentItemBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentMethodBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentMethodConfigBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.SenderBuilder;
import br.com.uol.pagseguro.api.common.domain.enums.ConfigKey;
import br.com.uol.pagseguro.api.common.domain.enums.Currency;
import br.com.uol.pagseguro.api.common.domain.enums.PaymentMethodGroup;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.exception.PagSeguroServerException;
import br.com.uol.pagseguro.api.http.HttpClient;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import br.com.uol.pagseguro.api.utils.logging.LoggerFactory;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;

public class PagSeguroTest {

  private String email = "lourival.sabino.junior@gmail.com";

  private String token = "5418C186C65646169DBA9624B32F8BA7";

  public PagSeguroTest() {
    super();
  }

  private PagSeguro getPagSeguro() {
    LoggerFactory loggerFactory = new SimpleLoggerFactory();
    HttpClient httpClient = new JSEHttpClient();
    Credential credential = Credential.sellerCredential(this.email, this.token);
    PagSeguroEnv pagSeguroEnv = PagSeguroEnv.SANDBOX;
    PagSeguro pagSeguro = PagSeguro.instance(loggerFactory, httpClient, credential, pagSeguroEnv);
    return pagSeguro;
  }

  private CheckoutRegistrationBuilder getCheckoutRegistrationBuilder() {
   // @formatter:off
    CheckoutRegistrationBuilder builder = new CheckoutRegistrationBuilder();
    builder.withCurrency(Currency.BRL);
    // builder.withExtraAmount(BigDecimal.ONE);
    builder.withReference(UUID.randomUUID().toString());
    builder.withSender(new SenderBuilder()
        .withEmail("c71924760558134326546@sandbox.pagseguro.com.br")
        .withName("José Vicente dos Santos")
        .withCPF("21034290100")
    );
    builder.addItem(new PaymentItemBuilder()
        .withId("1")
        .withQuantity(1)
        .withAmount(new BigDecimal(1234.56))
        .withDescription("Certificado de Teste 1")
        .withWeight(1)
    );
    builder.withAcceptedPaymentMethods(new AcceptedPaymentMethodsBuilder()
        .addInclude(new PaymentMethodBuilder().withGroup(PaymentMethodGroup.BANK_SLIP))
        .addInclude(new PaymentMethodBuilder().withGroup(PaymentMethodGroup.BALANCE))
        .addInclude(new PaymentMethodBuilder().withGroup(PaymentMethodGroup.CREDIT_CARD))
        .addInclude(new PaymentMethodBuilder().withGroup(PaymentMethodGroup.DEPOSIT))
        .addInclude(new PaymentMethodBuilder().withGroup(PaymentMethodGroup.ONLINE_DEBIT))
    );
    builder.addPaymentMethodConfig(new PaymentMethodConfigBuilder()
        .withPaymentMethod(new PaymentMethodBuilder().withGroup(PaymentMethodGroup.CREDIT_CARD))
        //.withConfig(new ConfigBuilder().withKey(ConfigKey.MAX_INSTALLMENTS_NO_INTEREST).withValue(new BigDecimal(3)))
        .withConfig(new ConfigBuilder().withKey(ConfigKey.MAX_INSTALLMENTS_LIMIT).withValue(new BigDecimal(12)))
    );
    // @formatter:on
    return builder;
  }

  @Test
  @Disabled
  public void test1() throws Exception {
    try {
      PagSeguro pagSeguro = this.getPagSeguro();
      CheckoutRegistrationBuilder builder = this.getCheckoutRegistrationBuilder();
      String url = pagSeguro.checkouts().register(builder).getRedirectURL();
      System.out.println(url);
    } catch (PagSeguroServerException e) {
      System.out.println(e.getServerResponse().asString());
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @Disabled
  public void test2() throws Exception {
    try {
      PagSeguro pagSeguro = this.getPagSeguro();
      CheckoutRegistrationBuilder builder = this.getCheckoutRegistrationBuilder();
      String code = pagSeguro.checkouts().register(builder).getCheckoutCode();
      System.out.println(code);
      TransactionDetail transaction = pagSeguro.transactions().search().byCode(code);
      System.out.println(transaction.getStatus());
    } catch (PagSeguroServerException e) {
      System.out.println(e.getServerResponse().asString());
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @Disabled
  public void test3() throws Exception {
    PagSeguro pagSeguro = this.getPagSeguro();
    System.out.println(pagSeguro.getHost());
    System.out.println(pagSeguro.getHostRedirect());
  }

  @Test
  @Disabled
  public void test4() throws Exception {
    PagSeguro pagSeguro = this.getPagSeguro();
    String url = String.format(Endpoints.CHECKOUT_REDIRECT_URL, pagSeguro.getHostRedirect(), "1234567890");
    System.out.println(url);
  }

  @Test
  public void test5() {
    try {
      PagSeguro pagSeguro = this.getPagSeguro();
      TransactionDetail transaction = pagSeguro.transactions().search().byCode("8BA0FCB643F0466DA9AE9B1176A52ACC");
      System.out.println(transaction.getStatus().getStatus().name());
    } catch (PagSeguroServerException e) {
      System.out.println(e.getServerResponse().asString());
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
