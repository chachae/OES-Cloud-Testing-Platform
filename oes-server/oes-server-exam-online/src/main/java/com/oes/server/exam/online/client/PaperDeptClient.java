package com.oes.server.exam.online.client;

import com.oes.common.core.constant.ServerConstant;
import com.oes.common.core.entity.R;
import com.oes.server.exam.online.client.fallback.PaperDeptClientFallback;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/23 12:00
 */
@FeignClient(
    contextId = ServerConstant.OES_SERVER_EXAM_BASIC + "-paper-dept",
    value = ServerConstant.OES_SERVER_EXAM_BASIC,
    fallbackFactory = PaperDeptClientFallback.class,
    path = "paper-dept"
)
public interface PaperDeptClient {

  @GetMapping("options")
  R<List<Long>> getPaperDeptListByPaperId(@RequestParam("paperId") Long paperId);
}
