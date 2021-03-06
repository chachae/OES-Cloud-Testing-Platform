package com.oes.ai.function.ocr.baidu.client;

import com.oes.ai.function.ocr.baidu.client.fallback.RemoteBaiduOcrServiceFallback;
import com.oes.ai.function.ocr.baidu.config.BaiduOcrInterceptorConfig;
import com.oes.ai.function.ocr.baidu.entity.AccessTokenInfo;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 15:55
 */
@FeignClient(
    name = "BaiduOcrRemoteService",
    url = "${oes.ai.baidu.ocr-url}",
    configuration = BaiduOcrInterceptorConfig.class,
    fallbackFactory = RemoteBaiduOcrServiceFallback.class
)
public interface BaiduOcrClient {

  /**
   * 卡证识别
   *
   * @return AccessToken 信息
   */
  @PostMapping
  ResponseEntity<Map<String, Object>> idCardOcr(
      @RequestParam(AccessTokenInfo.KEY_ACCESS_TOKEN) String accessToken, String res);

}
