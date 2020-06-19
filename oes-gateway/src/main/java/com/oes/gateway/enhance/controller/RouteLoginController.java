package com.oes.gateway.enhance.controller;

import com.oes.common.core.entity.R;
import com.oes.gateway.enhance.auth.JwtTokenHelper;
import com.oes.gateway.enhance.service.RouteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author chachae
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("route")
public class RouteLoginController {

  private final JwtTokenHelper tokenHelper;
  private final PasswordEncoder passwordEncoder;
  private final RouteUserService routeUserService;

  @GetMapping("login")
  public Mono<ResponseEntity<R<String>>> login(String username, String password) {
    String error = "认证失败，用户名或密码错误";
    return routeUserService.findByUsername(username)
        .map(u -> passwordEncoder.matches(password, u.getPassword()) ?
            ResponseEntity.ok(R.ok(tokenHelper.generateToken(u))) :
            new ResponseEntity<>(R.fail(error),
                HttpStatus.INTERNAL_SERVER_ERROR))
        .defaultIfEmpty(new ResponseEntity<>(R.fail(error),
            HttpStatus.INTERNAL_SERVER_ERROR));
  }
}
