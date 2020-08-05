package com.oes.server.exam.online.client;

import com.oes.common.core.constant.ServerConstant;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Paper;
import com.oes.server.exam.online.client.fallback.RemoteExamBasicPaperServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/23 12:00
 */
@FeignClient(
    value = ServerConstant.OES_SERVER_EXAM_BASIC,
    contextId = "oesExamBasicPaperServiceClient",
    fallbackFactory = RemoteExamBasicPaperServiceFallback.class
)
public interface PaperClient {

  @GetMapping("paper/{paperId}")
  R<Paper> getOne(@PathVariable("paperId") Long paperId);
}
