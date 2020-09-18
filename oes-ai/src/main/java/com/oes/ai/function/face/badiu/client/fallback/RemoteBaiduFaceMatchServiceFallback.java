package com.oes.ai.function.face.badiu.client.fallback;

import com.oes.ai.function.face.badiu.client.IRemoteBaiduFaceMatchService;
import com.oes.common.core.annotation.Fallback;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/9 21:33
 */
@Slf4j
@Fallback
public class RemoteBaiduFaceMatchServiceFallback implements
    FallbackFactory<IRemoteBaiduFaceMatchService> {

  @Override
  public IRemoteBaiduFaceMatchService create(Throwable throwable) {
    log.error("Baidu Face Match 人脸对比识别接口远端调用异常回滚:", throwable);
    return (accessToken, res) -> null;
  }
}
