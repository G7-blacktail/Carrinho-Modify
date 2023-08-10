package com.certificadoranacional.ac.core.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class TemplateServiceImpl implements TemplateService {

  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  public TemplateServiceImpl() {
  }

  @Override
  public String parse(final String template, final Map<String, Object> vars) {
    Context context = new Context();
    context.setVariables(vars);
    return this.templateEngine.process(template, context);
  }

}
