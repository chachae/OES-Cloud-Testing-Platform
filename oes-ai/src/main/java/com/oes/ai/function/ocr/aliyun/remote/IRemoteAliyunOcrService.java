package com.oes.ai.function.ocr.aliyun.remote;

import com.oes.ai.function.ocr.aliyun.config.AliyunOcrTokenConfig;
import com.oes.ai.function.ocr.aliyun.remote.fallback.RemoteAliyunOcrServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Aliyun OCR 卡证光学识别API远端调用
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 11:39
 */
@FeignClient(name = "AliyunIDCardOCR", url = "${oes.ai.aliyun.url}", configuration = AliyunOcrTokenConfig.class, fallbackFactory = RemoteAliyunOcrServiceFallback.class)
public interface IRemoteAliyunOcrService {

  @PostMapping
  String idCardOcr(String obj);

}

