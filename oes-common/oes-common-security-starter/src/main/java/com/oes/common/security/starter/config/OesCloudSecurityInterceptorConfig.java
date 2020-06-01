package com.oes.common.security.starter.config;

import com.oes.common.security.starter.interceptor.OesServerProtectInterceptor;
import com.oes.common.security.starter.properties.OesCloudSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chachae
 */
public class OesCloudSecurityInterceptorConfig implements WebMvcConfigurer {

  private OesCloudSecurityProperties properties;

  @Autowired
  public void setProperties(OesCloudSecurityProperties properties) {
    this.properties = properties;
  }

  @Bean
  public HandlerInterceptor oesServerProtectInterceptor() {
    OesServerProtectInterceptor interceptor = new OesServerProtectInterceptor();
    interceptor.setProperties(properties);
    return interceptor;
  }

  @Override
  @SuppressWarnings("all")
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(oesServerProtectInterceptor());
  }
}
