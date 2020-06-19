package com.oes.gateway.enhance.init;

import com.oes.gateway.enhance.service.BlackListService;
import com.oes.gateway.enhance.service.RateLimitRuleService;
import com.oes.gateway.enhance.service.RouteEnhanceCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @author chachae
 */
@RequiredArgsConstructor
public class OesRouteEnhanceRunner implements ApplicationRunner {

  private final RouteEnhanceCacheService cacheService;
  private final BlackListService blackListService;
  private final RateLimitRuleService rateLimitRuleService;

  @Override
  public void run(ApplicationArguments args) {
    System.out.println("已开启网关增强功能：请求日志、黑名单&限流。");
    cacheService.saveAllBlackList(blackListService.findAll());
    cacheService.saveAllRateLimitRules(rateLimitRuleService.findAll());
  }
}
