package com.oes.ai;

import com.oes.common.security.starter.annotation.EnableOesCloudResourceServer;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 11:35
 */
@EnableAsync
@EnableFeignClients
@SpringBootApplication
@EnableTransactionManagement
@EnableOesCloudResourceServer
public class AIApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(AIApplication.class)
        .web(WebApplicationType.SERVLET)
        .run(args);
  }
}
