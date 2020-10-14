package com.oes.server.exam.online.client.fallback;

import com.oes.common.core.annotation.Fallback;
import com.oes.server.exam.online.client.ViolateLogClient;
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
public class ViolateLogClientFallback implements
    FallbackFactory<ViolateLogClient> {

  @Override
  public ViolateLogClient create(Throwable throwable) {
    return paperId -> {
      log.error("远程调用失败", throwable);
      return null;
    };
  }
}