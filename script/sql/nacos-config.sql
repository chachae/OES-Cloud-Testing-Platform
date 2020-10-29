/*
 Navicat Premium Data Transfer

 Source Server         : aliyun-rds-1c1g
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Schema         : nacos-config

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 29/10/2020 15:35:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`
(
    `id`           bigint(20)                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(255) CHARACTER SET utf8 COLLATE utf8_bin          DEFAULT NULL,
    `content`      longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `md5`          varchar(32) CHARACTER SET utf8 COLLATE utf8_bin           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                                         NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified` datetime                                         NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    `src_user`     text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
    `src_ip`       varchar(20) CHARACTER SET utf8 COLLATE utf8_bin           DEFAULT NULL COMMENT 'source ip',
    `app_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin          DEFAULT NULL,
    `tenant_id`    varchar(128) CHARACTER SET utf8 COLLATE utf8_bin          DEFAULT '' COMMENT '租户字段',
    `c_desc`       varchar(256) CHARACTER SET utf8 COLLATE utf8_bin          DEFAULT NULL,
    `c_use`        varchar(64) CHARACTER SET utf8 COLLATE utf8_bin           DEFAULT NULL,
    `effect`       varchar(64) CHARACTER SET utf8 COLLATE utf8_bin           DEFAULT NULL,
    `type`         varchar(64) CHARACTER SET utf8 COLLATE utf8_bin           DEFAULT NULL,
    `c_schema`     text CHARACTER SET utf8 COLLATE utf8_bin,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 232
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='config_info';

-- ----------------------------
-- Records of config_info
-- ----------------------------
BEGIN;
INSERT INTO `config_info`
VALUES (7, 'OES-Admin.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 8400\n\nspring:\n  security:\n    user:\n      name: oes\n      password: 123456\n      \n  boot:\n    admin:\n      ui:\n        title: ${spring.application.name}',
        'f28862cbd00037a6f6bc12cf57cdb7a0', '2020-05-11 19:26:05', '2020-06-01 21:37:00', NULL, '106.15.202.13', '', '', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info`
VALUES (8, 'OES-Auth.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 9200\nspring:\n  thymeleaf:\n    cache: false\n  datasource:\n    dynamic:\n      hikari:\n        connection-timeout: 30000\n        max-lifetime: 1800000\n        max-pool-size: 15\n        min-idle: 5\n        connection-test-query: select 1\n        pool-name: OESHikariCP\n        data-source-properties:\n          serverTimezone: Asia/Shanghai\n          characterEncoding: utf-8\n          useUnicode: true\n          useSSL: false\n          autoReconnect: true\n          cachePrepStmts: true\n          useJDBCCompliantTimezoneShift: true\n          rewriteBatchedStatements: true\n          cacheResultSetMetadata: true\n          cacheServerConfiguration: true\n          elideSetAutoCommits: true\n          maintainTimeStats: false\n          allowPublicKeyRetrieval: true\n      primary: master\n      datasource:\n        master:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3306/oes-cloud-base\n        slave:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3307/oes-cloud-base   \n\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n  boot:\n    admin:\n      client:\n        url: http://${oes-admin}:8400\n        username: oes\n        password: 123456\n        instance:\n          prefer-ip: true\n\n  redis:\n    database: 1\n    host: ${redis.url}\n    port: 6379\n    lettuce:\n      pool:\n        min-idle: 8\n        max-idle: 500\n        max-active: 2000\n        max-wait: 10000\n    timeout: 5000\n    \nmybatis-plus:\n  configuration:\n    jdbc-type-for-null: null\n    map-underscore-to-camel-case: true\n  global-config:\n    banner: false\n    db-config:\n      id-type: auto\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\n\njustauth:\n  enabled: true\n  type:\n    github:\n      client-id: caf4644ad6d3070dab52\n      client-secret: 4410ff1d4335e1183166c653a477ccca0b3e6583\n      redirect-uri: http://api.cloudx.cn:8301/auth/social/github/callback\n\n    gitee:\n      client-id: 1f7714460552ac1260bace3204a91b55596b3289fac8d072375a6f03f6995673\n      client-secret: a87a6d242e47a635f38636790e110c848cff2ef722ddc4b05391ff4be456913f\n      redirect-uri: http://api.cloudx.cn:8301/auth/social/gitee/callback\n    tencent_cloud:\n      client-id:\n      client-secret:\n      redirect-uri:\n    dingtalk:\n      client-id:\n      client-secret:\n      redirect-uri:\n    qq:\n      client-id:\n      client-secret:\n      redirect-uri:\n    microsoft:\n      client-id:\n      client-secret:\n      redirect-uri:\n  cache:\n    type: redis\n    prefix: \'OES::CLOUD::SOCIAL::STATE::\'\n    timeout: 1h\n\noes:\n  frontUrl: \'http://localhost:9527\'\n  cloud:\n    security:\n      enable: true\n      only-fetch-by-gateway: false\n      anon-uris: /actuator/**,/captcha,/social/**,/v2/api-docs,/v2/api-docs-ext,/login,/resource/**,/oauth/token',
        '27463e2a80e7008b8b24ed2414da3fa1', '2020-05-11 19:51:55', '2020-09-13 11:52:37', NULL, '0:0:0:0:0:0:0:1', '', '', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info`
VALUES (9, 'OES-Gateway.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 8301\n  max-http-header-size: 30000\n\nspring:\n  cloud:\n    gateway:\n      globalcors:\n        corsConfigurations:\n          \'[/**]\':\n            allowedOrigins: \"*\"\n            allowedMethods: \"*\"\n            allowedHeaders: \"*\"\n            allowCredentials: true\n      routes:\n        - id: OES-Auth-Social\n          uri: lb://OES-Auth\n          predicates:\n            - Path=/auth/social/**\n        - id: OES-Auth\n          uri: lb://OES-Auth\n          predicates:\n            - Path=/auth/**\n        - id: OES-Server-System\n          uri: lb://OES-Server-System\n          predicates:\n            - Path=/system/**\n        - id: OES-Server-Demo\n          uri: lb://OES-Server-Demo\n          predicates:\n            - Path=/demo/**\n        - id: OES-Server-Job\n          uri: lb://OES-Server-Job\n          predicates:\n            - Path=/job/**\n        - id: OES-Server-Exam-Basic\n          uri: lb://OES-Server-Exam-Basic\n          predicates:\n            - Path=/exam-basic/**\n        - id: OES-Server-Exam-Online\n          uri: lb://OES-Server-Exam-Online\n          predicates:\n            - Path=/exam-online/**\n        - id: OES-Server-Exam-Online-WebSocket\n          uri: lb:ws://OES-Server-Exam-Online\n          predicates:\n            - Path=/ws-exam-online/**\n        - id: OES-OSS-Qiniu\n          uri: lb://OES-OSS-Qiniu\n          predicates:\n            - Path=/oss-qiniu/**\n        - id: OES-AI\n          uri: lb://OES-AI\n          predicates:\n            - Path=/ai/**\n      loadbalancer:\n        use404: true\n      # 转发前去掉一层访问路径\n      default-filters:\n        - StripPrefix=1\n\n  redis:\n    database: 3\n    host: ${redis.url}\n    port: 6379\n    lettuce:\n      pool:\n        min-idle: 8\n        max-idle: 500\n        max-active: 2000\n        max-wait: 10000\n    timeout: 5000\n    \n  autoconfigure:\n    exclude: org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration,org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration\n\n  # 网关增强配置\n  # data:\n  #   mongodb:\n  #     host: ${mongo.url}\n  #     port: 27017\n  #     database: oes_cloud_route\n\n  jackson:\n    date-format: yyyy-mm-dd HH:mm:ss\n\n  boot:\n    admin:\n      client:\n        url: http://${oes-admin}:8400\n        username: oes\n        password: 123456\n        instance:\n          prefer-ip: true\n\n# 网关增强配置\noes:\n  gateway:\n    enhance: false\n    jwt:\n      secret: 123456\n      expiration: 36000\n\nribbon:\n  eager-load:\n    enabled: true\n\nmanagement:\n  endpoint:\n    health:\n      show-details: ALWAYS\n  endpoints:\n    web:\n      exposure:\n        include: health,info,gateway\n',
        '0e291cc78258f1fa448c4b0bd42c705f', '2020-05-11 20:59:19', '2020-10-25 03:52:19', NULL, '0:0:0:0:0:0:0:1', '', '', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info`
VALUES (10, 'OES-Server-Demo.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 9501\nspring:\n  datasource:\n    dynamic:\n      p6spy: true\n      hikari:\n        connection-timeout: 30000\n        max-lifetime: 1800000\n        max-pool-size: 15\n        min-idle: 5\n        connection-test-query: select 1\n        pool-name: OESHikariCP\n      primary: base\n      datasource:\n        base:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3306/oes-cloud-base?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n    \n  boot:\n    admin:\n      client:\n        url: http://${oes-admin}:8400\n        username: oes\n        password: 123456\n        instance:\n          prefer-ip: true\n\nfeign:\n  hystrix:\n    enabled: true\n\nhystrix:\n  shareSecurityContext: true\n  command:\n    default:\n      execution:\n        isolation:\n          thread:\n            timeoutInMilliseconds: 3000\n\nmybatis-plus:\n  configuration:\n    jdbc-type-for-null: null\n    map-underscore-to-camel-case: true\n  global-config:\n    banner: false\n\nsecurity:\n  oauth2:\n    resource:\n      id: ${spring.application.name}\n      user-info-uri: http://${oes-gateway}:8301/auth/user\n\ntx-lcn:\n  client:\n    manager-address: ${oes-tx-manager}:8888\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\noes:\n  cloud:\n    security:\n      enable: true\n      anon-uris: /actuator/**',
        'cf8c7aadb12966f4abe749774c9d4821', '2020-05-11 21:06:26', '2020-06-03 21:12:32', NULL, '106.15.202.13', '', '', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info`
VALUES (11, 'OES-Server-System.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 9500\nspring:\n  datasource:\n    dynamic:\n      p6spy: true\n      hikari:\n        connection-timeout: 30000\n        max-lifetime: 1800000\n        max-pool-size: 15\n        min-idle: 5\n        connection-test-query: select 1\n        pool-name: OESHikariCP\n        data-source-properties:\n          serverTimezone: Asia/Shanghai\n          characterEncoding: utf-8\n          useUnicode: true\n          useSSL: false\n          autoReconnect: true\n          cachePrepStmts: true\n          useJDBCCompliantTimezoneShift: true\n          rewriteBatchedStatements: true\n          cacheResultSetMetadata: true\n          cacheServerConfiguration: true\n          elideSetAutoCommits: true\n          maintainTimeStats: false\n          allowPublicKeyRetrieval: true\n      primary: master\n      datasource:\n        master:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3306/oes-cloud-base\n        slave:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3307/oes-cloud-base\n          \n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n    \n  boot:\n    admin:\n      client:\n        url: http://${oes-admin}:8400\n        username: oes\n        password: 123456\n        instance:\n          prefer-ip: true\n\nfeign:\n  hystrix:\n    enabled: true\n\nhystrix:\n  shareSecurityContext: true\n  command:\n    default:\n      execution:\n        isolation:\n          thread:\n            timeoutInMilliseconds: 3000\n\nmybatis-plus:\n  configuration:\n    jdbc-type-for-null: null\n    map-underscore-to-camel-case: true\n  global-config:\n    banner: false\n\nsecurity:\n  oauth2:\n    resource:\n      id: ${spring.application.name}\n      user-info-uri: http://${oes-gateway}:8301/auth/user\n\n# tx-lcn:\n  # client:\n    # manager-address: ${oes-tx-manager}:8888\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\noes:\n  cloud:\n    security:\n      enable: true\n      anon-uris: /actuator/**',
        '6c805c90de3ab207fa905d554739fe5c', '2020-05-11 21:08:56', '2020-09-13 11:53:13', NULL, '0:0:0:0:0:0:0:1', '', '', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info`
VALUES (12, 'OES-TX-Manager.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 8501\nspring:\n  boot:\n    admin:\n      client:\n        url: http://${oes-admin}:8400\n        username: oes\n        password: 123456\n        instance:\n          prefer-ip: true\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://${mysql.url}:3306/oes-cloud-base?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\n    username: root\n    password: 123456\n  redis:\n    database: 0\n    host: ${redis.url}\n    port: 6379\n    lettuce:\n      pool:\n        min-idle: 8\n        max-idle: 500\n        max-active: 2000\n        max-wait: 10000\n    timeout: 5000\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\n\ntx-lcn:\n  manager:\n    host: 0.0.0.0\n    # TM监听Socket端口.\n    port: 8888\n    # TM控制台密码\n    admin-key: 123456\n  logger:\n    # 开启日志记录\n    enabled: true\n    driver-class-name: ${spring.datasource.driver-class-name}\n    jdbc-url: ${spring.datasource.url}\n    username: ${spring.datasource.username}\n    password: ${spring.datasource.password}',
        '83c1d2889ec23dc363bec0af57987eb6', '2020-06-01 00:29:23', '2020-06-01 22:45:31', NULL, '106.15.202.13', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (206, 'OES-Server-Exam-Basic.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 9502\nspring:\n  datasource:\n    dynamic:\n      p6spy: true\n      hikari:\n        connection-timeout: 30000\n        max-lifetime: 1800000\n        max-pool-size: 15\n        min-idle: 5\n        connection-test-query: select 1\n        pool-name: OESHikariCP\n        data-source-properties:\n          serverTimezone: Asia/Shanghai\n          characterEncoding: utf-8\n          useUnicode: true\n          useSSL: false\n          autoReconnect: true\n          cachePrepStmts: true\n          useJDBCCompliantTimezoneShift: true\n          rewriteBatchedStatements: true\n          cacheResultSetMetadata: true\n          cacheServerConfiguration: true\n          elideSetAutoCommits: true\n          maintainTimeStats: false\n          allowPublicKeyRetrieval: true\n      primary: master\n      datasource:\n        master:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3306/oes-cloud-exam\n        slave:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3307/oes-cloud-exam\n          \n  rabbitmq:\n    host: chachae.cn\n    port: 5672\n    username: chachae\n    password: 123\n    virtual-host: /oes\n    listener:\n      simple:\n        concurrency: 5  # 并发度 : 5\n        max-concurrency: 20 # 最大并发度 : 20\n        acknowledge-mode: manual\n        prefetch: 1\n    publisher-returns: true\n    publisher-confirm-type: correlated\n\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8 \n  boot:\n    admin:\n      client:\n        url: http://${oes-admin}:8400\n        username: oes\n        password: 123456\n        instance:\n          prefer-ip: true\n  redis:\n    database: 0\n    host: ${redis.url}\n    port: 6379\n    lettuce:\n      pool:\n        min-idle: 8\n        max-idle: 500\n        max-active: 2000\n        max-wait: 10000\n    timeout: 5000\n    \nfeign:\n  hystrix:\n    enabled: true\n\nhystrix:\n  shareSecurityContext: true\n  command:\n    default:\n      execution:\n        isolation:\n          thread:\n            timeoutInMilliseconds: 3000\n\nmybatis-plus:\n  configuration:\n    jdbc-type-for-null: null\n    map-underscore-to-camel-case: true\n  global-config:\n    banner: false\n\nsecurity:\n  oauth2:\n    resource:\n      id: ${spring.application.name}\n      user-info-uri: http://${oes-gateway}:8301/auth/user\n\n# tx-lcn:\n  # client:\n    # manager-address: ${oes-tx-manager}:8888\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\noes:\n  cloud:\n    security:\n      enable: true\n      anon-uris: /actuator/**,/type/**',
        'c582819ea85dab3a5f8391cfc62e8c79', '2020-06-19 21:30:11', '2020-10-19 11:33:24', NULL, '0:0:0:0:0:0:0:1', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (209, 'OES-Server-Exam-Online.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 9503\nws:\n  port: 10086\nspring:\n  datasource:\n    dynamic:\n      p6spy: true\n      hikari:\n        connection-timeout: 30000\n        max-lifetime: 1800000\n        max-pool-size: 15\n        min-idle: 5\n        connection-test-query: select 1\n        pool-name: OESHikariCP\n        data-source-properties:\n          serverTimezone: Asia/Shanghai\n          characterEncoding: utf-8\n          useUnicode: true\n          useSSL: false\n          autoReconnect: true\n          cachePrepStmts: true\n          useJDBCCompliantTimezoneShift: true\n          rewriteBatchedStatements: true\n          cacheResultSetMetadata: true\n          cacheServerConfiguration: true\n          elideSetAutoCommits: true\n          maintainTimeStats: false\n          allowPublicKeyRetrieval: true\n      primary: master\n      datasource:\n        master:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3306/oes-cloud-exam\n        slave:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3307/oes-cloud-exam\n          \n  rabbitmq:\n    host: chachae.cn\n    port: 5672\n    username: chachae\n    password: 123\n    virtual-host: /oes\n    listener:\n      simple:\n        concurrency: 5  # 并发度 : 5\n        max-concurrency: 20 # 最大并发度 : 20\n        acknowledge-mode: manual\n        prefetch: 1\n    publisher-returns: true\n    publisher-confirm-type: correlated\n\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n    \n  boot:\n    admin:\n      client:\n        url: http://${oes-admin}:8400\n        username: oes\n        password: 123456\n        instance:\n          prefer-ip: true\n  redis:\n    database: 0\n    host: ${redis.url}\n    port: 6379\n    lettuce:\n      pool:\n        min-idle: 8\n        max-idle: 500\n        max-active: 2000\n        max-wait: 10000\n    timeout: 5000\nfeign:\n  hystrix:\n    enabled: true\n\nhystrix:\n  shareSecurityContext: true\n  command:\n    default:\n      execution:\n        isolation:\n          thread:\n            timeoutInMilliseconds: 3000\n\nmybatis-plus:\n  configuration:\n    jdbc-type-for-null: null\n    map-underscore-to-camel-case: true\n  global-config:\n    banner: false\n\nsecurity:\n  oauth2:\n    resource:\n      id: ${spring.application.name}\n      user-info-uri: http://${oes-gateway}:8301/auth/user\n\n# tx-lcn:\n  # client:\n    # manager-address: ${oes-tx-manager}:8888\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\noes:\n  cloud:\n    security:\n      enable: true\n      anon-uris: /actuator/**',
        '9214e703ec64fe8e2bd46ea872585169', '2020-06-19 21:57:38', '2020-10-19 11:33:38', NULL, '0:0:0:0:0:0:0:1', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (219, 'OES-Server-Job.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 8204\nspring:\n  datasource:\n    dynamic:\n      p6spy: true\n      hikari:\n        connection-timeout: 30000\n        max-lifetime: 1800000\n        max-pool-size: 15\n        min-idle: 5\n        connection-test-query: select 1\n        pool-name: OESHikariCP\n        data-source-properties:\n          serverTimezone: Asia/Shanghai\n          characterEncoding: utf-8\n          useUnicode: true\n          useSSL: false\n          autoReconnect: true\n          cachePrepStmts: true\n          useJDBCCompliantTimezoneShift: true\n          rewriteBatchedStatements: true\n          cacheResultSetMetadata: true\n          cacheServerConfiguration: true\n          elideSetAutoCommits: true\n          maintainTimeStats: false\n          allowPublicKeyRetrieval: true\n      primary: master\n      datasource:\n        master:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3306/oes-cloud-job\n        slave:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3307/oes-cloud-job\n          \n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n    \n  boot:\n    admin:\n      client:\n        url: http://${oes-admin}:8400\n        username: oes\n        password: 123456\n        instance:\n          prefer-ip: true\n\nfeign:\n  hystrix:\n    enabled: true\n\nhystrix:\n  shareSecurityContext: true\n  command:\n    default:\n      execution:\n        isolation:\n          thread:\n            timeoutInMilliseconds: 3000\n\nmybatis-plus:\n  configuration:\n    jdbc-type-for-null: null\n    map-underscore-to-camel-case: true\n  global-config:\n    banner: false\n\nsecurity:\n  oauth2:\n    resource:\n      id: ${spring.application.name}\n      user-info-uri: http://${oes-gateway}:8301/auth/user\n\n# tx-lcn:\n  # client:\n    # manager-address: ${oes-tx-manager}:8888\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\noes:\n  cloud:\n    security:\n      enable: true\n      anon-uris: /actuator/**',
        'a7929e0e2472773e79ccf949ade16ab7', '2020-06-25 15:00:01', '2020-09-21 08:19:40', NULL, '0:0:0:0:0:0:0:1', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (223, 'OES-OSS-Qiniu.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 9505\nspring:\n  datasource:\n    dynamic:\n      p6spy: true\n      hikari:\n        connection-timeout: 30000\n        max-lifetime: 1800000\n        max-pool-size: 15\n        min-idle: 5\n        connection-test-query: select 1\n        pool-name: OESHikariCP\n        data-source-properties:\n          serverTimezone: Asia/Shanghai\n          characterEncoding: utf-8\n          useUnicode: true\n          useSSL: false\n          autoReconnect: true\n          cachePrepStmts: true\n          useJDBCCompliantTimezoneShift: true\n          rewriteBatchedStatements: true\n          cacheResultSetMetadata: true\n          cacheServerConfiguration: true\n          elideSetAutoCommits: true\n          maintainTimeStats: false\n          allowPublicKeyRetrieval: true\n      primary: master\n      datasource:\n        master:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3306/oes-cloud-oss\n        slave:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3307/oes-cloud-oss\n\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n    \n  boot:\n    admin:\n      client:\n        url: http://${oes-admin}:8400\n        username: oes\n        password: 123456\n        instance:\n          prefer-ip: true\n  redis:\n    database: 0\n    host: ${redis.url}\n    port: 6379\n    lettuce:\n      pool:\n        min-idle: 8\n        max-idle: 500\n        max-active: 2000\n        max-wait: 10000\n    timeout: 5000\nfeign:\n  hystrix:\n    enabled: true\n\nhystrix:\n  shareSecurityContext: true\n  command:\n    default:\n      execution:\n        isolation:\n          thread:\n            timeoutInMilliseconds: 3000\n\nmybatis-plus:\n  configuration:\n    jdbc-type-for-null: null\n    map-underscore-to-camel-case: true\n  global-config:\n    banner: false\n\nsecurity:\n  oauth2:\n    resource:\n      id: ${spring.application.name}\n      user-info-uri: http://${oes-gateway}:8301/auth/user\n\n# tx-lcn:\n  # client:\n    # manager-address: ${oes-tx-manager}:8888\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\noes:\n  cloud:\n    security:\n      enable: true\n      anon-uris: /actuator/**\n      \n#七牛云\nqiNiu:\n  # 文件大小 /M\n  max-size: 15\n  expire-in-second: 3600',
        '25310133027ba268812074c21acf9e18', '2020-06-27 11:35:39', '2020-09-13 11:54:14', NULL, '0:0:0:0:0:0:0:1', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (229, 'OES-AI.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 9700\nspring:\n  \n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n    \n  redis:\n    database: 0\n    host: ${redis.url}\n    port: 6379\n    lettuce:\n      pool:\n        min-idle: 8\n        max-idle: 500\n        max-active: 2000\n        max-wait: 10000\n    timeout: 5000\n\n  boot:\n    admin:\n      client:\n        url: http://${oes-admin}:8400\n        username: oes\n        password: 123456\n        instance:\n          prefer-ip: true\n\nfeign:\n  hystrix:\n    enabled: true\n  httpclient:\n    enabled: true\n    \nhystrix:\n  shareSecurityContext: true\n  command:\n    default:\n      execution:\n        isolation:\n          thread:\n            timeoutInMilliseconds: 3000\n\nsecurity:\n  oauth2:\n    resource:\n      id: ${spring.application.name}\n      user-info-uri: http://${oes-gateway}:8301/auth/user\nmybatis-plus:\n  configuration:\n    jdbc-type-for-null: null\n    map-underscore-to-camel-case: true\n  global-config:\n    banner: false\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\noes:\n  cloud:\n    security:\n      enable: true\n      anon-uris: /actuator/**\n  ai: \n    supplier: baidu\n    aliyun:\n      url: http://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json\n      appcode: APPCODE 419e93c5fc9d4a89877ea734001df716\n    baidu:\n      auth-url: https://aip.baidubce.com/oauth/2.0/token\n      ocr-url: https://aip.baidubce.com/rest/2.0/ocr/v1/idcard\n      face-url: https://aip.baidubce.com/rest/2.0/face/v3/match\n      api-key: zH1HTYrfAyT1OIZd0WG8Wp6u\n      secret-key: YNFT1r2aNGLXxQhb2chMQyjARE38OUFO\n      grant-type: client_credentials\n',
        '991e950688a60805910b3b32304001ac', '2020-06-29 11:46:18', '2020-08-10 16:02:53', NULL, '106.15.202.13', '', '', '', '', '', 'yaml', '');
COMMIT;

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`
(
    `id`           bigint(20)                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `datum_id`     varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
    `content`      longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL COMMENT '内容',
    `gmt_modified` datetime                                         NOT NULL COMMENT '修改时间',
    `app_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    `tenant_id`    varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='增加租户字段';

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`
(
    `id`           bigint(20)                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `app_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `beta_ips`     varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin         DEFAULT NULL COMMENT 'betaIps',
    `md5`          varchar(32) CHARACTER SET utf8 COLLATE utf8_bin           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                                         NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified` datetime                                         NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    `src_user`     text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
    `src_ip`       varchar(20) CHARACTER SET utf8 COLLATE utf8_bin           DEFAULT NULL COMMENT 'source ip',
    `tenant_id`    varchar(128) CHARACTER SET utf8 COLLATE utf8_bin          DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='config_info_beta';

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`
(
    `id`           bigint(20)                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id`    varchar(128) CHARACTER SET utf8 COLLATE utf8_bin          DEFAULT '' COMMENT 'tenant_id',
    `tag_id`       varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
    `app_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `md5`          varchar(32) CHARACTER SET utf8 COLLATE utf8_bin           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                                         NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified` datetime                                         NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    `src_user`     text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
    `src_ip`       varchar(20) CHARACTER SET utf8 COLLATE utf8_bin           DEFAULT NULL COMMENT 'source ip',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='config_info_tag';

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`
(
    `id`        bigint(20)                                       NOT NULL COMMENT 'id',
    `tag_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
    `tag_type`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin  DEFAULT NULL COMMENT 'tag_type',
    `data_id`   varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`  varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
    `nid`       bigint(20)                                       NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`nid`) USING BTREE,
    UNIQUE KEY `uk_configtagrelation_configidtag` (`id`, `tag_name`, `tag_type`) USING BTREE,
    KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='config_tag_relation';

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`
(
    `id`                bigint(20) unsigned                              NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `group_id`          varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
    `quota`             int(10) unsigned                                 NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int(10) unsigned                                 NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int(10) unsigned                                 NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) unsigned                                 NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
    `max_aggr_size`     int(10) unsigned                                 NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) unsigned                                 NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime                                         NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified`      datetime                                         NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_group_id` (`group_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='集群、各Group容量信息表';

-- ----------------------------
-- Records of group_capacity
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`
(
    `id`           bigint(20) unsigned                              NOT NULL,
    `nid`          bigint(20) unsigned                              NOT NULL AUTO_INCREMENT,
    `data_id`      varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `group_id`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `app_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL,
    `md5`          varchar(32) CHARACTER SET utf8 COLLATE utf8_bin           DEFAULT NULL,
    `gmt_create`   datetime                                         NOT NULL DEFAULT '2010-05-05 00:00:00',
    `gmt_modified` datetime                                         NOT NULL DEFAULT '2010-05-05 00:00:00',
    `src_user`     text CHARACTER SET utf8 COLLATE utf8_bin,
    `src_ip`       varchar(20) CHARACTER SET utf8 COLLATE utf8_bin           DEFAULT NULL,
    `op_type`      char(10) CHARACTER SET utf8 COLLATE utf8_bin              DEFAULT NULL,
    `tenant_id`    varchar(128) CHARACTER SET utf8 COLLATE utf8_bin          DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`nid`) USING BTREE,
    KEY `idx_gmt_create` (`gmt_create`) USING BTREE,
    KEY `idx_gmt_modified` (`gmt_modified`) USING BTREE,
    KEY `idx_did` (`data_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 313
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='多租户改造';

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`
(
    `role`     varchar(50)  NOT NULL,
    `resource` varchar(512) NOT NULL,
    `action`   varchar(8)   NOT NULL,
    UNIQUE KEY `uk_role_permission` (`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of permissions
-- ----------------------------
BEGIN;
INSERT INTO `permissions`
VALUES ('ROLE_GUEST', ':*:*', 'r');
COMMIT;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`
(
    `username` varchar(50) NOT NULL,
    `role`     varchar(50) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
BEGIN;
INSERT INTO `roles`
VALUES ('nacos', 'ROLE_ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`
(
    `id`                bigint(20) unsigned                              NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`         varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
    `quota`             int(10) unsigned                                 NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int(10) unsigned                                 NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int(10) unsigned                                 NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) unsigned                                 NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
    `max_aggr_size`     int(10) unsigned                                 NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) unsigned                                 NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime                                         NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified`      datetime                                         NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='租户容量信息表';

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`
(
    `id`            bigint(20)                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `kp`            varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
    `tenant_id`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
    `tenant_name`   varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
    `tenant_desc`   varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
    `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin  DEFAULT NULL COMMENT 'create_source',
    `gmt_create`    bigint(20)                                       NOT NULL COMMENT '创建时间',
    `gmt_modified`  bigint(20)                                       NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`, `tenant_id`) USING BTREE,
    KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='tenant_info';

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `username` varchar(50)  NOT NULL,
    `password` varchar(500) NOT NULL,
    `enabled`  tinyint(1)   NOT NULL,
    PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users`
VALUES ('nacos', '$2a$10$1GRYceIFqRdkjo0Ltxv0iegWI/DD7PF.j/9Ruc5ixlznDn3bSiXLC', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
