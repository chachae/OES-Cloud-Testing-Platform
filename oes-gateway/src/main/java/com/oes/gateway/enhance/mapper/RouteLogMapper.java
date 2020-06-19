package com.oes.gateway.enhance.mapper;

import com.oes.gateway.enhance.entity.RouteLog;
import java.util.Collection;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author chachae
 */
@Repository
public interface RouteLogMapper extends ReactiveMongoRepository<RouteLog, String> {

  /**
   * 删除路由日志
   *
   * @param ids 路由日志id
   * @return 被删除的路由日志
   */
  Flux<RouteLog> deleteByIdIn(Collection<String> ids);
}
