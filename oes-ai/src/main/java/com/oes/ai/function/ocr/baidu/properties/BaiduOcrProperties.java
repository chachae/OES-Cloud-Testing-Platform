package com.oes.ai.function.ocr.baidu.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 16:12
 */
@Data
@SpringBootConfiguration
@ConfigurationProperties(prefix = "oes.ai.baidu")
public class BaiduOcrProperties {

  private String authUrl;

  private String ocrUrl;

  private String apiKey;

  private String secretKey;

  private String grantType;

}
