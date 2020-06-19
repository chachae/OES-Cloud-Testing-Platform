package com.oes.gateway.enhance.mapper;

import com.oes.gateway.enhance.entity.RateLimitRule;
import java.util.Collection;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author chachae
 */
@Repository
public interface RateLimitRuleMapper extends ReactiveMongoRepository<RateLimitRule, String> {

  /**
   * 删除限流规则
   *
   * @param ids 限流规则id
   * @return 被删除的限流规则
   */
  Flux<RateLimitRule> deleteByIdIn(Collection<String> ids);

  /**
   * 删除限流规则
   *
   * @param requestUri    requestUri
   * @param requestMethod requestMethod
   * @return 被删除的限流规则
   */
  Flux<RateLimitRule> findByRequestUriAndRequestMethod(String requestUri, String requestMethod);
}
