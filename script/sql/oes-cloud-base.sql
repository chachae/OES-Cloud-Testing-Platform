/*
 Navicat Premium Data Transfer

 Source Server         : aliyun-rds-1c1g
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Schema         : oes-cloud-base

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 29/10/2020 15:44:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`
(
    `client_id`               varchar(255) NOT NULL,
    `resource_ids`            varchar(255)  DEFAULT NULL,
    `client_secret`           varchar(255) NOT NULL,
    `scope`                   varchar(255) NOT NULL,
    `authorized_grant_types`  varchar(255) NOT NULL,
    `web_server_redirect_uri` varchar(255)  DEFAULT NULL,
    `authorities`             varchar(255)  DEFAULT NULL,
    `access_token_validity`   int(11)      NOT NULL,
    `refresh_token_validity`  int(11)       DEFAULT NULL,
    `additional_information`  varchar(4096) DEFAULT NULL,
    `autoapprove`             tinyint(4)    DEFAULT NULL,
    `origin_secret`           varchar(255)  DEFAULT NULL,
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`
(
    `create_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `code`           varchar(255)       DEFAULT NULL,
    `authentication` blob,
    KEY `code_index` (`code`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_announce
-- ----------------------------
DROP TABLE IF EXISTS `t_announce`;
CREATE TABLE `t_announce`
(
    `announce_id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '公告主键',
    `title`        varchar(100) DEFAULT NULL COMMENT '公告标题',
    `content_id`   bigint(20)   DEFAULT NULL COMMENT '内容编号',
    `creator_name` varchar(100) DEFAULT NULL COMMENT '创建人',
    `status`       tinyint(4)   DEFAULT NULL COMMENT '公告状态（1：激活，0：禁用）',
    `create_time`  datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`  datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`announce_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 23
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统公告表';

-- ----------------------------
-- Table structure for t_announce_content
-- ----------------------------
DROP TABLE IF EXISTS `t_announce_content`;
CREATE TABLE `t_announce_content`
(
    `content_id`   bigint(20) NOT NULL AUTO_INCREMENT,
    `html_content` blob COMMENT '公告内容',
    PRIMARY KEY (`content_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 23
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统公告内容表';

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept`
(
    `dept_id`     bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '部门ID',
    `parent_id`   bigint(20)   NOT NULL COMMENT '上级部门ID',
    `dept_name`   varchar(100) NOT NULL COMMENT '部门名称',
    `order_num`   int(11)  DEFAULT NULL COMMENT '排序',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`dept_id`) USING BTREE,
    UNIQUE KEY `t_dept_id` (`dept_id`) USING BTREE COMMENT '主键索引'
) ENGINE = InnoDB
  AUTO_INCREMENT = 104
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='部门表';

-- ----------------------------
-- Table structure for t_idcard_verify
-- ----------------------------
DROP TABLE IF EXISTS `t_idcard_verify`;
CREATE TABLE `t_idcard_verify`
(
    `verify_id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     int(11)      DEFAULT NULL COMMENT '用户编号',
    `name`        varchar(100) DEFAULT NULL COMMENT '身份证姓名',
    `address`     varchar(100) DEFAULT NULL COMMENT '身份证地址',
    `num`         varchar(25)  DEFAULT NULL COMMENT '身份证号',
    `sex`         varchar(10)  DEFAULT NULL COMMENT '身份证性别',
    `birth`       varchar(10)  DEFAULT NULL COMMENT '身份证生日',
    `photo`       blob COMMENT '身份证头像base64编码',
    `start_date`  varchar(50)  DEFAULT NULL COMMENT '身份证签发日期',
    `end_date`    varchar(50)  DEFAULT NULL COMMENT '身份证过期时间',
    `issue`       varchar(255) DEFAULT NULL COMMENT '身份证签发机关',
    `success`     tinyint(4)   DEFAULT NULL COMMENT '身份核验状态（1：通过，0：失败）',
    `fake`        tinyint(4)   DEFAULT NULL COMMENT '是否为复印件等',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`verify_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_job
-- ----------------------------
DROP TABLE IF EXISTS `t_job`;
CREATE TABLE `t_job`
(
    `job_id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '任务id',
    `bean_name`       varchar(50) NOT NULL COMMENT 'spring bean名称',
    `method_name`     varchar(50) NOT NULL COMMENT '方法名',
    `params`          varchar(50) DEFAULT NULL COMMENT '参数',
    `cron_expression` varchar(20) NOT NULL COMMENT 'cron表达式',
    `status`          char(2)     NOT NULL COMMENT '任务状态  0：正常  1：暂停',
    `remark`          varchar(50) DEFAULT NULL COMMENT '备注',
    `create_time`     datetime    DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`job_id`) USING BTREE,
    KEY `t_job_create_time` (`create_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='定时任务表';

-- ----------------------------
-- Table structure for t_job_log
-- ----------------------------
DROP TABLE IF EXISTS `t_job_log`;
CREATE TABLE `t_job_log`
(
    `log_id`      bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
    `job_id`      bigint(20)   NOT NULL COMMENT '任务id',
    `bean_name`   varchar(100) NOT NULL COMMENT 'spring bean名称',
    `method_name` varchar(100) NOT NULL COMMENT '方法名',
    `params`      varchar(200)   DEFAULT NULL COMMENT '参数',
    `status`      char(2)      NOT NULL COMMENT '任务状态    0：成功    1：失败',
    `error`       text COMMENT '失败信息',
    `times`       decimal(11, 0) DEFAULT NULL COMMENT '耗时(单位：毫秒)',
    `create_time` datetime       DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`log_id`) USING BTREE,
    KEY `t_job_log_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='调度日志表';

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `username`    varchar(50)    DEFAULT NULL COMMENT '操作用户',
    `operation`   text COMMENT '操作内容',
    `time`        decimal(11, 0) DEFAULT NULL COMMENT '耗时',
    `method`      text COMMENT '操作方法',
    `params`      text COMMENT '方法参数',
    `ip`          varchar(64)    DEFAULT NULL COMMENT '操作者IP',
    `create_time` datetime       DEFAULT NULL COMMENT '创建时间',
    `location`    varchar(50)    DEFAULT NULL COMMENT '操作地点',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `t_log_create_time` (`create_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 246
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='用户操作日志表';

-- ----------------------------
-- Table structure for t_logger
-- ----------------------------
DROP TABLE IF EXISTS `t_logger`;
CREATE TABLE `t_logger`
(
    `id`          bigint(20)    NOT NULL AUTO_INCREMENT,
    `group_id`    varchar(64)   NOT NULL,
    `unit_id`     varchar(32)   NOT NULL,
    `tag`         varchar(50)   NOT NULL,
    `content`     varchar(1024) NOT NULL,
    `create_time` varchar(30)   NOT NULL,
    `app_name`    varchar(128)  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_login_log`;
CREATE TABLE `t_login_log`
(
    `id`         bigint(20)  NOT NULL AUTO_INCREMENT COMMENT 'id',
    `username`   varchar(50) NOT NULL COMMENT '用户名',
    `login_time` datetime    NOT NULL COMMENT '登录时间',
    `location`   varchar(50) DEFAULT NULL COMMENT '登录地点',
    `ip`         varchar(50) DEFAULT NULL COMMENT 'IP地址',
    `system`     varchar(80) DEFAULT NULL COMMENT '操作系统',
    `browser`    varchar(80) DEFAULT NULL COMMENT '浏览器',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `t_login_log_login_time` (`login_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 155
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='登录日志表';

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`
(
    `menu_id`     bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
    `parent_id`   bigint(20)  NOT NULL COMMENT '上级菜单ID',
    `menu_name`   varchar(50) NOT NULL COMMENT '菜单/按钮名称',
    `path`        varchar(255)  DEFAULT NULL COMMENT '对应路由path',
    `component`   varchar(255)  DEFAULT NULL COMMENT '对应路由组件component',
    `perms`       varchar(50)   DEFAULT NULL COMMENT '权限表达式',
    `icon`        varchar(50)   DEFAULT NULL COMMENT '图标',
    `type`        char(2)     NOT NULL COMMENT '类型 0菜单 1按钮',
    `order_num`   double(20, 0) DEFAULT NULL COMMENT '排序',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `update_time` datetime      DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`menu_id`) USING BTREE,
    KEY `t_menu_parent_id` (`parent_id`),
    KEY `t_menu_menu_id` (`menu_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 243
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='菜单表';

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`
(
    `role_id`     bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`   varchar(10) NOT NULL COMMENT '角色名称',
    `remark`      varchar(100) DEFAULT NULL COMMENT '角色描述',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='角色表';

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`
(
    `role_id` bigint(20) NOT NULL,
    `menu_id` bigint(20) NOT NULL,
    PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='角色菜单关联表';

-- ----------------------------
-- Table structure for t_tx_exception
-- ----------------------------
DROP TABLE IF EXISTS `t_tx_exception`;
CREATE TABLE `t_tx_exception`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT,
    `group_id`          varchar(64)    DEFAULT NULL,
    `unit_id`           varchar(32)    DEFAULT NULL,
    `mod_id`            varchar(128)   DEFAULT NULL,
    `transaction_state` tinyint(4)     DEFAULT NULL,
    `registrar`         tinyint(4)     DEFAULT NULL,
    `ex_state`          tinyint(4)     DEFAULT NULL COMMENT '0 待处理 1已处理',
    `remark`            varchar(10240) DEFAULT NULL COMMENT '备注',
    `create_time`       datetime       DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `user_id`         bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`        varchar(50)  NOT NULL COMMENT '用户名',
    `full_name`       varchar(50)  DEFAULT NULL COMMENT '真实姓名',
    `password`        varchar(128) NOT NULL COMMENT '密码',
    `dept_id`         bigint(20)   DEFAULT NULL COMMENT '部门ID',
    `email`           varchar(128) DEFAULT NULL COMMENT '邮箱',
    `mobile`          varchar(20)  DEFAULT NULL COMMENT '联系电话',
    `status`          char(1)      NOT NULL COMMENT '状态 0锁定 1有效',
    `create_time`     datetime     NOT NULL COMMENT '创建时间',
    `update_time`     datetime     DEFAULT NULL COMMENT '修改时间',
    `last_login_time` datetime     DEFAULT NULL COMMENT '最近访问时间',
    `ssex`            char(1)      DEFAULT NULL COMMENT '性别 0男 1女 2保密',
    `is_tab`          char(1)      DEFAULT NULL COMMENT '是否开启tab，0关闭 1开启',
    `theme`           varchar(10)  DEFAULT NULL COMMENT '主题',
    `avatar`          varchar(100) DEFAULT NULL COMMENT '头像',
    `description`     varchar(100) DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE KEY `t_user_id` (`user_id`) USING BTREE COMMENT '主键索引',
    KEY `t_user_mobile` (`mobile`) USING BTREE COMMENT '联系方式索引',
    KEY `t_user_username` (`username`) USING BTREE COMMENT '用户名',
    CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user_role` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2140
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='用户表';

-- ----------------------------
-- Table structure for t_user_connection
-- ----------------------------
DROP TABLE IF EXISTS `t_user_connection`;
CREATE TABLE `t_user_connection`
(
    `user_name`          varchar(50) NOT NULL COMMENT 'cloudx系统用户名',
    `provider_name`      varchar(20) NOT NULL COMMENT '第三方平台名称',
    `provider_user_id`   varchar(50) NOT NULL COMMENT '第三方平台账户ID',
    `provider_user_name` varchar(50)  DEFAULT NULL COMMENT '第三方平台用户名',
    `nick_name`          varchar(50)  DEFAULT NULL COMMENT '第三方平台昵称',
    `image_url`          varchar(512) DEFAULT NULL COMMENT '第三方平台头像',
    `location`           varchar(255) DEFAULT NULL COMMENT '地址',
    `remark`             varchar(255) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`user_name`, `provider_name`, `provider_user_id`) USING BTREE,
    UNIQUE KEY `UserConnectionRank` (`user_name`, `provider_name`, `provider_user_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='系统用户社交账户关联表';

-- ----------------------------
-- Table structure for t_user_data_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_user_data_permission`;
CREATE TABLE `t_user_data_permission`
(
    `user_id` bigint(20) NOT NULL,
    `dept_id` bigint(20) NOT NULL,
    PRIMARY KEY (`user_id`, `dept_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户数据权限关联表';

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`
(
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`role_id`, `user_id`),
    KEY `user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='用户角色关联表';

SET FOREIGN_KEY_CHECKS = 1;
