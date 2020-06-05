package com.oes.common.security.starter.config;

import cn.hutool.core.util.StrUtil;
import com.oes.common.security.starter.handler.OesAccessDeniedHandler;
import com.oes.common.security.starter.handler.OesAuthExceptionEntryPoint;
import com.oes.common.security.starter.properties.OesCloudSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.util.Base64Utils;

/**
 * @author chachae
 */
@EnableResourceServer
@EnableAutoConfiguration(exclude = UserDetailsServiceAutoConfiguration.class)
public class OesCloudResourceServerConfig extends ResourceServerConfigurerAdapter {

  private OesCloudSecurityProperties properties;
  private OesAccessDeniedHandler accessDeniedHandler;
  private OesAuthExceptionEntryPoint exceptionEntryPoint;

  @Autowired
  public void setProperties(OesCloudSecurityProperties properties) {
    this.properties = properties;
  }

  @Autowired
  public void setAccessDeniedHandler(OesAccessDeniedHandler accessDeniedHandler) {
    this.accessDeniedHandler = accessDeniedHandler;
  }

  @Autowired
  public void setExceptionEntryPoint(OesAuthExceptionEntryPoint exceptionEntryPoint) {
    this.exceptionEntryPoint = exceptionEntryPoint;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    String[] anonUrls = StrUtil.splitToArray(properties.getAnonUris(), ',');
    if (anonUrls.length == 0) {
      anonUrls = new String[]{};
    }

    http.csrf().disable()
        .requestMatchers().antMatchers(properties.getAuthUri())
        .and()
        .authorizeRequests()
        .antMatchers(anonUrls).permitAll()
        .antMatchers(properties.getAuthUri()).authenticated()
        .and()
        .httpBasic();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.authenticationEntryPoint(exceptionEntryPoint)
        .accessDeniedHandler(accessDeniedHandler);
  }

  public static void main(String[] args) {
    System.out.println(new String((Base64Utils.encode("oes:123456".getBytes()))));
  }
}
