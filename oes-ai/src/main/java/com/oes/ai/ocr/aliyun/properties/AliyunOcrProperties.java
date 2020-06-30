package com.oes.ai.ocr.aliyun.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 12:20
 */
@Data
@SpringBootConfiguration
@ConfigurationProperties(prefix = "oes.ocr.aliyun")
public class AliyunOcrProperties {

  /**
   * 接口
   */
  private String url;

  /**
   * app code
   */
  private String appcode;
}
