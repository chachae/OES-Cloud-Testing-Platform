package com.oes.ai.function.ocr.baidu.remote.fallback;

import com.oes.ai.function.ocr.baidu.remote.IRemoteBaiduOcrService;
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
public class RemoteBaiduOcrServiceFallback implements FallbackFactory<IRemoteBaiduOcrService> {

  @Override
  public IRemoteBaiduOcrService create(Throwable throwable) {
    return (accessToken, res) -> {
      log.error("Baidu OCR 光学识别接口远端调用异常回滚:", throwable);
      return null;
    };
  }
}
