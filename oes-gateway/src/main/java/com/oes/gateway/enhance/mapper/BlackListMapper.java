package com.oes.gateway.enhance.mapper;

import com.oes.gateway.enhance.entity.BlackList;
import java.util.Collection;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author chachae
 */
@Repository
public interface BlackListMapper extends ReactiveMongoRepository<BlackList, String> {

  /**
   * 删除黑名单
   *
   * @param ids 黑名单id
   * @return 被删除的黑名单
   */
  Flux<BlackList> deleteByIdIn(Collection<String> ids);

  /**
   * 查询黑名单
   *
   * @param ip            ip
   * @param requestUri    requestUri
   * @param requestMethod requestMethod
   * @return 黑名单
   */
  Flux<BlackList> findByIpAndRequestUriAndRequestMethod(String ip, String requestUri,
      String requestMethod);

  /**
   * 查询黑名单
   *
   * @param requestUri    requestUri
   * @param requestMethod requestMethod
   * @return 黑名单
   */
  Flux<BlackList> findByRequestUriAndRequestMethod(String requestUri, String requestMethod);

}
