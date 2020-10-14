package com.oes.server.exam.online.client.fallback;

import com.oes.common.core.annotation.Fallback;
import com.oes.server.exam.online.client.PaperQuestionClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author chachae
 * @date 2020/9/29 16:57
 * @version v1.0
 */
@Slf4j
@Fallback
public class PaperQuestionClientClientFallback implements FallbackFactory<PaperQuestionClient> {

  @Override
  public PaperQuestionClient create(Throwable throwable) {
    return paperId -> {
      log.error("远程调用失败", throwable);
      return null;
    };
  }
}