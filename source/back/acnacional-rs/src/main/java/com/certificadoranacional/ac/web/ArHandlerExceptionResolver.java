package com.certificadoranacional.ac.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.certificadoranacional.ac.core.Log;
import com.google.common.base.Throwables;

@Component
public class ArHandlerExceptionResolver extends SimpleMappingExceptionResolver implements InitializingBean {

  public ArHandlerExceptionResolver() {
    super();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.setDefaultErrorView("error");
  }

  @Override
  public ModelAndView resolveException(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) {
    Log.getLog().info(ex.getMessage(), ex);
    ModelAndView model = super.doResolveException(request, response, handler, ex);
    model.addObject("error", Throwables.getRootCause(ex).getMessage());
    model.addObject("stacktrace", Throwables.getStackTraceAsString(ex));
    model.addObject("url", request.getRequestURL());
    model.setViewName("error");
    return model;
  }

}
