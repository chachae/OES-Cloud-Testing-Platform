package com.oes.server.exam.online.client.fallback;

import com.oes.common.core.annotation.Fallback;
import com.oes.server.exam.online.client.PaperClient;
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
public class RemoteExamBasicPaperServiceFallback implements
    FallbackFactory<PaperClient> {

  @Override
  public PaperClient create(Throwable throwable) {
    return paperId -> {
      log.error("远程调用失败", throwable);
      return null;
    };
  }
}