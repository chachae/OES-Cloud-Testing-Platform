package com.oes.server.exam.online.client;

import com.oes.common.core.constant.ServerConstant;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.server.exam.online.client.fallback.PaperQuestionClientClientFallback;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author chachae
 * @date 2020/9/29 16:56
 * @version v1.0
 */
@FeignClient(
    path = "paper-question",
    contextId = ServerConstant.OES_SERVER_EXAM_BASIC + "-paper-question",
    value = ServerConstant.OES_SERVER_EXAM_BASIC,
    fallbackFactory = PaperQuestionClientClientFallback.class
)
public interface PaperQuestionClient {

  @GetMapping("list")
  R<Map<Long, PaperQuestion>> getMap(@RequestParam("paperId") Long paperId);
}
