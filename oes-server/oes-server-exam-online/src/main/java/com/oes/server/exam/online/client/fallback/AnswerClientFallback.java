package com.oes.server.exam.online.client.fallback;

import com.oes.common.core.annotation.Fallback;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Answer;
import com.oes.server.exam.online.client.AnswerClient;
import feign.hystrix.FallbackFactory;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Feign 异常回滚
 *
 * @author chachae
 * @since 2020/05/01 11:57
 */
@Slf4j
@Fallback
public class AnswerClientFallback implements FallbackFactory<AnswerClient> {

  @Override
  public AnswerClient create(Throwable throwable) {
    return new AnswerClient() {

      @Override
      public void update(Answer answer) {
        log.error("远程调用失败", throwable);
      }

      @Override
      public void add(Answer answer) {
        log.error("远程调用失败", throwable);
      }

      @Override
      public R<List<Map<String, Object>>> getWarningAnswer(Long paperId) {
        log.error("远程调用失败", throwable);
        return null;
      }

      @Override
      public R<List<Answer>> getAnswerList(String username, Long paperId) {
        log.error("远程调用失败", throwable);
        return null;
      }
    };
  }
}