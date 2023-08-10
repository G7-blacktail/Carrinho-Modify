package com.certificadoranacional.ac.core.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.certificadoranacional.ac.core.Log;

@Service
public class MailServiceImpl implements MailService {

  @Autowired
  private JavaMailSender sender;

  public MailServiceImpl() {
    super();
  }

  @Override
  public MimeMessage create() {
    return this.sender.createMimeMessage();
  }

  @Override
  public boolean send(final MimeMessage message) {
    try {
      this.sender.send(message);
      return true;
    } catch (Exception e) {
      Log.getLog().debug(e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean send(final SimpleMailMessage message) {
    try {
      this.sender.send(message);
      return true;
    } catch (Exception e) {
      Log.getLog().warn(e.getMessage(), e);
      return false;
    }
  }

}
