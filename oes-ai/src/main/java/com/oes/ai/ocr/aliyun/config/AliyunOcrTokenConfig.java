package com.oes.ai.ocr.aliyun.config;

import com.oes.ai.ocr.aliyun.properties.AliyunOcrProperties;
import com.oes.common.core.constant.GatewayConstant;
import com.oes.common.core.constant.SystemConstant;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Base64Utils;

/**
 * Aliyun - OCR 卡证光学识别接口请求认证配置
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 11:50
 */

@RequiredArgsConstructor
public class AliyunOcrTokenConfig {

  private final AliyunOcrProperties properties;

  @Bean
  public RequestInterceptor oauth2FeignRequestInterceptor() {
    return requestTemplate -> {
      String gatewayToken = new String(Base64Utils.encode(GatewayConstant.TOKEN_VALUE.getBytes()));
      requestTemplate
          .header(HttpHeaders.CONTENT_TYPE, SystemConstant.JSON_HEADER)
          .header(GatewayConstant.TOKEN_HEADER, gatewayToken)
          .header(HttpHeaders.AUTHORIZATION, properties.getAppcode());
    };
  }
}
