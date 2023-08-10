package com.certificadoranacional.ac.core.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

public interface MailService {

  MimeMessage create();

  boolean send(MimeMessage message);

  boolean send(SimpleMailMessage message);

}
