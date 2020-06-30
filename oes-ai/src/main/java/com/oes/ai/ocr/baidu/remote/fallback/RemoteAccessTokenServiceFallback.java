package com.oes.ai.ocr.baidu.remote.fallback;

import com.oes.ai.ocr.baidu.remote.IRemoteAccessTokenService;
import com.oes.common.core.annotation.Fallback;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 15:56
 */
@Slf4j
@Fallback
public class RemoteAccessTokenServiceFallback implements
    FallbackFactory<IRemoteAccessTokenService> {

  @Override
  public IRemoteAccessTokenService create(Throwable throwable) {
    return (grantType, apiKey, secretKey) -> {
      log.error("RemoteAccessTokenServiceFallback:", throwable);
      return null;
    };
  }
}
