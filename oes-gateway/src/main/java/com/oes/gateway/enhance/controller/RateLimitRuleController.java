package com.oes.gateway.enhance.controller;

import com.oes.common.core.entity.QueryParam;
import com.oes.gateway.enhance.entity.RateLimitRule;
import com.oes.gateway.enhance.service.RateLimitRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author chachae
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("route/auth/rateLimitRule")
public class RateLimitRuleController {

  private final RateLimitRuleService rateLimitRuleService;

  @GetMapping("data")
  public Flux<RateLimitRule> findUserPages(QueryParam request, RateLimitRule rateLimitRule) {
    return rateLimitRuleService.findPages(request, rateLimitRule);
  }

  @GetMapping("count")
  public Mono<Long> findUserCount(RateLimitRule rateLimitRule) {
    return rateLimitRuleService.findCount(rateLimitRule);
  }

  @GetMapping("exist")
  public Flux<RateLimitRule> findByRequestUriAndRequestMethod(String requestUri,
      String requestMethod) {
    return rateLimitRuleService.findByRequestUriAndRequestMethod(requestUri, requestMethod);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('admin')")
  public Mono<RateLimitRule> createRateLimitRule(RateLimitRule rateLimitRule) {
    return rateLimitRuleService.create(rateLimitRule);
  }

  @PutMapping
  @PreAuthorize("hasAuthority('admin')")
  public Mono<RateLimitRule> updateRateLimitRule(RateLimitRule rateLimitRule) {
    return rateLimitRuleService.update(rateLimitRule);
  }

  @DeleteMapping
  @PreAuthorize("hasAuthority('admin')")
  public Flux<RateLimitRule> deleteRateLimitRule(String ids) {
    return rateLimitRuleService.delete(ids);
  }
}
