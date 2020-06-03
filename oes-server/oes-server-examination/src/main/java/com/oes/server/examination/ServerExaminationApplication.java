package com.oes.server.examination;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.oes.common.security.starter.annotation.EnableOesCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chachae
 * @since 2020/4/29 17:53
 */
@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@EnableDistributedTransaction
@EnableOesCloudResourceServer
@MapperScan("com.oes.server.examination.mapper")
public class ServerExaminationApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(ServerExaminationApplication.class)
        .web(WebApplicationType.SERVLET)
        .run(args);
  }
}

