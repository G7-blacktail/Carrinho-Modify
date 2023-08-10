package com.certificadoranacional.ac.core.service;

import java.util.Map;

public interface TemplateService {

  String parse(String template, Map<String, Object> vars);

}
