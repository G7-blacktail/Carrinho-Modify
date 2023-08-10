package com.certificadoranacional.ac;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

  public CorsFilter() {
    super();
  }

  @Override
  public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Allow-Headers", "Authorization, Access-Control-Allow-Headers, Content-Type, Origin, X-Requested-With, X-XSRF-TOKEN");
    response.setHeader("Access-Control-Max-Age", "3600");
    if (!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
      chain.doFilter(req, res);
    }
  }

  @Override
  public void init(final FilterConfig filterConfig) {
    //
  }

  @Override
  public void destroy() {
    //
  }

}
