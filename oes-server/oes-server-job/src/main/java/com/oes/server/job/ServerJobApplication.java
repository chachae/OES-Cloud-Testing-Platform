package com.oes.server.job;

import com.oes.common.security.starter.annotation.EnableOesCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author chachae
 */
@SpringBootApplication
@EnableOesCloudResourceServer
@MapperScan("com.oes.server.job.mapper")
public class ServerJobApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(ServerJobApplication.class)
        .web(WebApplicationType.SERVLET)
        .run(args);
  }

}
