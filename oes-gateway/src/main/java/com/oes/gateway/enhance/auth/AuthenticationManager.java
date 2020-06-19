package com.oes.gateway.enhance.auth;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author chachae
 */
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

  private final JwtTokenHelper tokenHelper;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String token = authentication.getCredentials().toString();
    String username;
    try {
      username = tokenHelper.getUsernameFromToken(token);
    } catch (Exception e) {
      username = null;
    }
    if (StrUtil.isNotBlank(username) && tokenHelper.validateToken(token)) {
      Claims claims = tokenHelper.getAllClaimsFromToken(token);
      String permissions = claims.get("permission", String.class);
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username,
          null,
          AuthorityUtils.commaSeparatedStringToAuthorityList(permissions)
      );
      return Mono.just(auth);
    } else {
      return Mono.empty();
    }
  }
}
