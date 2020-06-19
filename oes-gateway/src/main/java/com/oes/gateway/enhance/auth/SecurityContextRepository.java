package com.oes.gateway.enhance.auth;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.constant.SystemConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author chachae
 */
@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

  private final AuthenticationManager authenticationManager;

  @Override
  public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
    throw new UnsupportedOperationException("暂不支持");
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
    ServerHttpRequest request = serverWebExchange.getRequest();
    String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (StrUtil.isNotBlank(authHeader) && StrUtil
        .startWith(authHeader, SystemConstant.OAUTH2_TOKEN_TYPE)) {
      String authToken = StrUtil.subAfter(authHeader, SystemConstant.OAUTH2_TOKEN_TYPE, true)
          .trim();
      Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
      return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
    } else {
      return Mono.empty();
    }
  }
}
