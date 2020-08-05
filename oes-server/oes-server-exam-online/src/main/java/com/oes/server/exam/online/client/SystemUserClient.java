package com.oes.server.exam.online.client;

import com.oes.common.core.constant.ServerConstant;
import com.oes.common.core.entity.R;
import com.oes.server.exam.online.client.fallback.RemoteSystemUserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/8/5 17:45
 */
@FeignClient(
    value = ServerConstant.OES_SERVER_SYSTEM,
    contextId = ServerConstant.OES_SERVER_SYSTEM,
    fallbackFactory = RemoteSystemUserServiceFallback.class
)
public interface SystemUserClient {

  @GetMapping("user/count")
  R<Integer> countByDeptIds(@RequestParam("deptIds") String deptIds);
}
