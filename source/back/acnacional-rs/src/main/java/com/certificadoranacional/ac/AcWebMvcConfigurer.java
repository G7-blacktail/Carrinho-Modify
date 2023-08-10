package com.certificadoranacional.ac;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class AcWebMvcConfigurer implements WebMvcConfigurer {

  public AcWebMvcConfigurer() {
    super();
  }

  @Override
  public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
    PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();
    pageableResolver.setFallbackPageable(PageRequest.of(0, 5));
    pageableResolver.setMaxPageSize(100);
    pageableResolver.setPageParameterName("p");
    pageableResolver.setSizeParameterName("s");
    argumentResolvers.add(pageableResolver);

    // SortHandlerMethodArgumentResolver sortResolver = new SortHandlerMethodArgumentResolver();
    // sortResolver.setFallbackSort(SpringRepositoryHelper.ID_SORT);
    // sortResolver.setSortParameter("o");
    // argumentResolvers.add(sortResolver);
  }

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    // @formatter:off
    registry
      .addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods("DELETE", "GET", "OPTIONS", "POST", "PUT")
      .allowedHeaders("Accept", "Access-Control-Allow-Headers", "Authorization", "Content-Type", "Origin", "X-Requested-With", "X-XSRF-TOKEN")
      .maxAge(3600);
    // @formatter:on
  }

  @Override
  public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Override
  public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
    configurer.favorParameter(false);
  }

}
