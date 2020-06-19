package com.oes.gateway.common.filter;

import cn.hutool.core.util.BooleanUtil;
import com.oes.common.core.constant.GatewayConstant;
import com.oes.gateway.enhance.service.RouteEnhanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Gateway 网关全局过滤器
 *
 * @author chachae
 * @since 2020/04/21 13:30
 */
@Slf4j
@Component
@Order(0)
@RequiredArgsConstructor
public class OesGatewayRequestFilter implements GlobalFilter {

  private final RouteEnhanceService routeEnhanceService;
  private final AntPathMatcher pathMatcher = new AntPathMatcher();
  @Value("${oes.gateway.enhance:false}")
  private Boolean routerEnhance;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    if (BooleanUtil.isTrue(routerEnhance)) {
      Mono<Void> blackListResult = routeEnhanceService.filterBlackList(exchange);
      if (blackListResult != null) {
        routeEnhanceService.saveBlockLogs(exchange);
        return blackListResult;
      }
      Mono<Void> rateLimitResult = routeEnhanceService.filterRateLimit(exchange);
      if (rateLimitResult != null) {
        routeEnhanceService.saveRateLimitLogs(exchange);
        return rateLimitResult;
      }
      routeEnhanceService.saveRequestLogs(exchange);
    }

    byte[] token = Base64Utils.encode((GatewayConstant.TOKEN_VALUE).getBytes());
    String[] headerValues = {new String(token)};
    ServerHttpRequest build = exchange.getRequest().mutate()
        .header(GatewayConstant.TOKEN_HEADER, headerValues).build();
    ServerWebExchange newExchange = exchange.mutate().request(build).build();
    return chain.filter(newExchange);
  }
}
