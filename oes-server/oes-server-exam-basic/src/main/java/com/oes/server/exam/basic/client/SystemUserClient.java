package com.oes.server.exam.basic.client;

import com.oes.common.core.constant.ServerConstant;
import com.oes.common.core.entity.R;
import com.oes.server.exam.basic.client.fallback.SystemUserClientFallback;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/11/23 18:45
 */
@FeignClient(
    value = ServerConstant.OES_SERVER_SYSTEM,
    path = "user",
    contextId = ServerConstant.OES_SERVER_SYSTEM + "-user",
    fallbackFactory = SystemUserClientFallback.class
)
public interface SystemUserClient {

  @GetMapping("user-id")
  R<List<Long>> getUserIdsByDeptIds(@RequestParam("deptIds") String deptIds);
}
