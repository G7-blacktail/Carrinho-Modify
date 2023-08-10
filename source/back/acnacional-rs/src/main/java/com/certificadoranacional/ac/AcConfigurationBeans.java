package com.certificadoranacional.ac;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class AcConfigurationBeans {

  public AcConfigurationBeans() {
    super();
  }

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    jsonConverter.setObjectMapper(objectMapper);
    return jsonConverter;
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate(this.clientHttpRequestFactory());
  }

  private ClientHttpRequestFactory clientHttpRequestFactory() {
    // @formatter:off
    CacheConfig cacheConfig = CacheConfig.custom()
        .setMaxCacheEntries(4096)
        .setMaxObjectSize(8192 * 1000)
        .build();
    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(30000)
        .setSocketTimeout(120000)
        .build();
    CloseableHttpClient httpClient = CachingHttpClients.custom()
        .setCacheConfig(cacheConfig)
        .setDefaultRequestConfig(requestConfig)
        .build();
    // @formatter:on

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
    factory.setConnectTimeout(30000);
    factory.setReadTimeout(120000);
    return factory;
  }

}
