package com.oes.gateway.enhance.mapper;

import com.oes.gateway.enhance.entity.RateLimitLog;
import java.util.Collection;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author chachae
 */
@Repository
public interface RateLimitLogMapper extends ReactiveMongoRepository<RateLimitLog, String> {

  /**
   * 删除限流日志
   *
   * @param ids 限流日志id
   * @return 被删除的限流日志
   */
  Flux<RateLimitLog> deleteByIdIn(Collection<String> ids);
}
