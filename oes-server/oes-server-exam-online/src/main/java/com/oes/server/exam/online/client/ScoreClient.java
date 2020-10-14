package com.oes.server.exam.online.client;

import com.oes.common.core.constant.ServerConstant;
import com.oes.common.core.entity.R;
import com.oes.server.exam.online.client.fallback.ScoreClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author chachae
 * @date 2020/9/29 16:29
 * @version v1.0
 */
@FeignClient(
    path = "score",
    contextId = ServerConstant.OES_SERVER_EXAM_BASIC + "-score",
    value = ServerConstant.OES_SERVER_EXAM_BASIC,
    fallbackFactory = ScoreClientFallback.class
)
public interface ScoreClient {

  @GetMapping("count")
  R<Integer> countByPaperId(@RequestParam("paperId") String paperId);
}
