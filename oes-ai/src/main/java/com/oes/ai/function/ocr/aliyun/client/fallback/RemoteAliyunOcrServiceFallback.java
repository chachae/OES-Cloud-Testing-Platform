package com.oes.ai.function.ocr.aliyun.client.fallback;

import com.oes.ai.function.ocr.aliyun.client.AliyunOcrClient;
import com.oes.common.core.annotation.Fallback;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * Feign 异常回滚
 *
 * @author chachae
 * @since 2020/05/01 11:57
 */
@Slf4j
@Fallback
public class RemoteAliyunOcrServiceFallback implements FallbackFactory<AliyunOcrClient> {

  @Override
  public AliyunOcrClient create(Throwable throwable) {
    return obj -> {
      if (throwable.getMessage().startsWith("[463 463] during [POST]")) {
        log.error("输入图像不是对应服务的图像，请上传正确的身份证图片");
      } else if (throwable.getMessage().startsWith("[400 400] during [POST]")) {
        log.error("URL错误");
      } else if (throwable.getMessage().startsWith("[450 450] during [POST]")) {
        log.error("后端服务队列满，请求被拒绝，重试即可");
      }
      return null;
    };
  }
}