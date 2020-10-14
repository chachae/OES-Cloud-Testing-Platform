package com.oes.server.exam.online.client;

import com.oes.common.core.constant.ServerConstant;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Answer;
import com.oes.server.exam.online.client.fallback.AnswerClientFallback;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author chachae
 * @date 2020/9/28 20:42
 * @version v1.0
 */
@FeignClient(
    path = "answer",
    contextId = ServerConstant.OES_SERVER_EXAM_BASIC + "-answer",
    value = ServerConstant.OES_SERVER_EXAM_BASIC,
    fallbackFactory = AnswerClientFallback.class
)
public interface AnswerClient {

  @PutMapping
  void update(@SpringQueryMap Answer answer);

  @PostMapping
  void add(@SpringQueryMap Answer answer);

  @GetMapping("warn")
  R<List<Map<String, Object>>> getWarningAnswer(@RequestParam("paperId") Long paperId);

  @GetMapping("list")
  R<List<Answer>> getAnswerList(@RequestParam("username") String username, @RequestParam("paperId") Long paperId);
}
