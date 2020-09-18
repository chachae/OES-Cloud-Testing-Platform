package com.oes.ai.client.fallback;

import com.oes.ai.client.IdCardVerifyClient;
import com.oes.common.core.annotation.Fallback;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * Feign 异常回滚
 *
 * @author chachae
 * @since 2020/05/01 11:57
 */
@Slf4j
@Fallback
public class IdCardVerifyClientFallback implements
    FallbackFactory<IdCardVerifyClient> {

  @Override
  public IdCardVerifyClient create(Throwable throwable) {
    return () -> {
      log.error("远程调用失败", throwable);
      return null;
    };
  }
}