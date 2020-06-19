package com.oes.gateway.enhance.configure;

import com.oes.common.core.constant.SystemConstant;
import com.oes.gateway.enhance.init.OesRouteEnhanceRunner;
import com.oes.gateway.enhance.service.BlackListService;
import com.oes.gateway.enhance.service.RateLimitRuleService;
import com.oes.gateway.enhance.service.RouteEnhanceCacheService;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author chachae
 */
@EnableAsync
@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.oes.gateway.enhance.mapper")
@ConditionalOnProperty(name = "oes.gateway.enhance", havingValue = "true")
public class OesRouteEnhanceConfigure {

  @Bean(SystemConstant.ASYNC_POOL)
  public ThreadPoolTaskExecutor asyncThreadPoolTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(20);
    executor.setQueueCapacity(100);
    executor.setKeepAliveSeconds(30);
    executor.setThreadNamePrefix("OES-Gateway-Async-Thread");
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(60);
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.initialize();
    return executor;
  }

  @Bean
  public ApplicationRunner oesRouteEnhanceRunner(RouteEnhanceCacheService cacheService,
      BlackListService blackListService,
      RateLimitRuleService rateLimitRuleService) {
    return new OesRouteEnhanceRunner(cacheService, blackListService, rateLimitRuleService);
  }
}
