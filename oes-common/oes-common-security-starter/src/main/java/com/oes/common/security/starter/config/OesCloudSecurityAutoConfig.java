package com.oes.common.security.starter.config;

import com.oes.common.core.constant.GatewayConstant;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.util.SecurityUtil;
import com.oes.common.security.starter.handler.OesAccessDeniedHandler;
import com.oes.common.security.starter.handler.OesAuthExceptionEntryPoint;
import com.oes.common.security.starter.properties.OesCloudSecurityProperties;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Base64Utils;

/**
 * @author chachae
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(OesCloudSecurityProperties.class)
@ConditionalOnProperty(value = "oes.cloud.security.enable", havingValue = "true", matchIfMissing = true)
public class OesCloudSecurityAutoConfig {

  @Bean
  @ConditionalOnMissingBean(name = "accessDeniedHandler")
  public OesAccessDeniedHandler accessDeniedHandler() {
    return new OesAccessDeniedHandler();
  }

  @Bean
  @ConditionalOnMissingBean(name = "authenticationEntryPoint")
  public OesAuthExceptionEntryPoint authenticationEntryPoint() {
    return new OesAuthExceptionEntryPoint();
  }

  @Bean
  @ConditionalOnMissingBean(value = PasswordEncoder.class)
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public OesCloudSecurityInterceptorConfig oesCloudSecurityInterceptorConfigure() {
    return new OesCloudSecurityInterceptorConfig();
  }

  /**
   * Feign 远程调用请求拦截，加入GatewayToken 和 当前用户的 Authorization Token
   *
   * @return RequestInterceptor
   */
  @Bean
  public RequestInterceptor oauth2FeignRequestInterceptor() {
    return requestTemplate -> {
      String gatewayToken = new String(Base64Utils.encode(GatewayConstant.TOKEN_VALUE.getBytes()));
      requestTemplate
          .header(GatewayConstant.TOKEN_HEADER, gatewayToken)
          .header(HttpHeaders.AUTHORIZATION,
              SystemConstant.OAUTH2_TOKEN_TYPE + SecurityUtil.getCurrentTokenValue());
    };
  }
}
