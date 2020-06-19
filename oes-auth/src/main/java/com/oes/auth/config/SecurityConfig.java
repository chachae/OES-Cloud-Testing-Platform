package com.oes.auth.config;

import com.oes.auth.filter.ValidateCodeFilter;
import com.oes.auth.handler.OesWebLoginFailureHandler;
import com.oes.auth.handler.OesWebLoginSuccessHandler;
import com.oes.common.core.constant.EndpointConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * WebSecurity 配置
 *
 * @author chachae
 * @since 2020/04/21
 */
@Order(2)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final ValidateCodeFilter validateCodeFilter;
  private final UserDetailsService userDetailService;
  private final OesWebLoginSuccessHandler successHandler;
  private final OesWebLoginFailureHandler failureHandler;
  private final PasswordEncoder passwordEncoder;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
        .requestMatchers()
        .antMatchers(EndpointConstant.OAUTH_ALL, EndpointConstant.LOGIN)
        .and()
        .authorizeRequests()
        .antMatchers(EndpointConstant.OAUTH_ALL).authenticated()
        .and()
        .formLogin()
        .loginPage(EndpointConstant.LOGIN)
        .loginProcessingUrl(EndpointConstant.LOGIN)
        .successHandler(successHandler)
        .failureHandler(failureHandler)
        .permitAll()
        .and().csrf().disable()
        .httpBasic().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
  }
}
