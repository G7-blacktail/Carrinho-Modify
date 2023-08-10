package com.certificadoranacional.ac.web.resources.v10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.certificadoranacional.ac.core.service.PagSeguroService;

@RestController
@RequestMapping(path = "/api/v1.0/pagseguro")
@CrossOrigin
public class PagSeguroResource {

  @Autowired
  private PagSeguroService pagSeguroService;

  public PagSeguroResource() {
    super();
  }

  @RequestMapping(path = "/return", method = RequestMethod.GET)
  public ResponseEntity<?> onReturn(@RequestParam(name = "transaction_id") final String transactionId) {
    this.pagSeguroService.checkTransaction(transactionId);
    return null;
  }

  @SuppressWarnings("unused")
  @RequestMapping(path = "/notify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<?> onNotify(@RequestParam(name = "notificationCode") final String notificationCode,
      @RequestParam(name = "notificationType") final String notificationType) {
    this.pagSeguroService.checkNotification(notificationCode);
    return null;
  }

}
