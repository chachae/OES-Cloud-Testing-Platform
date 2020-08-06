package com.oes.ai.function.ocr.aliyun.client;

import com.oes.ai.function.ocr.aliyun.client.fallback.RemoteAliyunOcrServiceFallback;
import com.oes.ai.function.ocr.aliyun.config.AliyunOcrTokenConfig;
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
public interface AliyunOcrClient {

  @PostMapping
  String idCardOcr(String obj);

}

