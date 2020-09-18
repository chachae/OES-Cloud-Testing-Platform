package com.oes.ai.client;

import com.oes.ai.client.fallback.IdCardVerifyClientFallback;
import com.oes.common.core.constant.ServerConstant;
import com.oes.common.core.entity.R;
import com.oes.common.core.entity.system.IdCardVerify;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 身份核验表数据库访问层
 *
 * @author chachae
 * @since 2020-06-30 22:33:13
 */
@FeignClient(
    value = ServerConstant.OES_SERVER_SYSTEM,
    contextId = ServerConstant.OES_SERVER_SYSTEM,
    fallbackFactory = IdCardVerifyClientFallback.class
)
public interface IdCardVerifyClient {

  @GetMapping("id-card/verify/me")
  R<IdCardVerify> getMyIdCardVerifyInfo();
}