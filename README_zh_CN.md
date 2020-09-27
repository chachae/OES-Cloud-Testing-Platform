### OES-Cloud
![https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square](https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/SpringCloud-Hoxton.RELEASE-yellow.svg?style=flat-square](https://img.shields.io/badge/SpringCloud-Hoxton.RELEASE-yellow.svg?style=flat-square)
![https://img.shields.io/badge/SpringCloudAlibaba-2.2.1.RELEASE-blueviolet.svg?style=flat-square](https://img.shields.io/badge/SpringCloudAlibaba-2.2.1.RELEASE-blueviolet.svg?style=flat-square)
![https://img.shields.io/badge/SpringBoot-2.2.6.RELEASE-brightgreen.svg?style=flat-square](https://img.shields.io/badge/SpringBoot-2.2.6.RELEASE-brightgreen.svg?style=flat-square)
![https://img.shields.io/badge/vue-2.6.10-orange.svg?style=flat-square](https://img.shields.io/badge/vue-2.6.10-orange.svg?style=flat-square)
![https://tokei.rs/b1/github/chachae/oes-cloud?category=lines](https://tokei.rs/b1/github/chachae/oes-cloud?category=lines)

OES-Cloud 是一个基于分布式微服务架构的高性能高校在线考试平台，本项目为该在线考试平台的服务端总控工程。

### 特性总览
序号 | 特点
---|---
1 | 前后端分离架构，客户端和服务端token认证交互
2 | 认证服务器与资源服务器分离，方便接入自己的微服务系统
3 | 集成Prometheus，SpringBootAdmin，Skywalking APM
4 | 网关限流，容错，降级，网关黑名单限制和网关日志
5 | 第三方社交登录
6 | OAuth2.0认证授权，独立SSO单点登录，资源服务独立域控
7 | 集成Seata、TxLCN等分布式事务解决方案
8 | 经过高度封装的百度、阿里卡证OCR光学识别、人脸对比识别等可配置化解决方案
9 | 基于MyBatis插件的可插拔注解数据权限化
10 | 考试过程的全自动化控制与消息队列数据处理方案
11 | 在线考试身份认核验、数据脱敏，考试行为全端监控和异常行为日志跟踪与记录
12 | 主观相似度计算和答案相似度流式计算解决方案
13 | 多层次模块化和外部化、可配置化架构设计，多种设计模式的融合
14 | 系统多平台应用，支持PC端和小程序端

### 系统架构图
<table>
<tr>
    <td align="center" style="background: #fff"><b>OES-Cloud</b></td>
  </tr>
  <tr>
    <td align="center" style="background: #fff"><img src="images/oes-cloud-framework-1.png"/></td>
  </tr>
</table>

### 项目地址

 平台  | 地址
---|---
服务端 | [https://github.com/chachae/oes-cloud](https://github.com/chachae/oes-cloud)
PC端 | [https://github.com/chachae/oes-web](https://github.com/chachae/oes-web)
小程序 | [https://github.com/chachae/oes-cloud-weapp](https://github.com/chachae/oes-cloud-weapp)

### 服务模块

OES-Cloud模块：

服务名称 | 端口 | 描述
---|---|---
OES-AI| 9700| 图像识别与生物检测系统
OES-Auth| 9200| 微服务认证服务器 
OES-Server-System| 9500 | 微服务子系统，系统核心模块
OES-Server-Exam-Basic| 9502 | 微服务子系统，系在线考试基础能力模块
OES-Server-Exam-Online| 9503 | 微服务子系统，在线考试模块
OES-Server-Demo|9501 | 微服务子系统，演示模块
OES-Server-Job|9504 | 微服务子系统，任务调度模块
OES-Gateway|8301|微服务网关
OES-Admin|8400|微服务监控子系统
OES-Tx-Manager|8501|微服务分布式事务控制系统


第三方模块：

服务名称 | 端口 | 描述
---|---|---
NACOS| 8300 |注册中心与配置中心 
MySQL| 3306 |MySQL 数据库 
Redis| 6379 | K-V 缓存数据库 
Elasticsearch|9200 | 日志存储
Logstash|4560|日志收集
Kibana|5601|日志展示
Prometheus|8403~8409|Prometheus APM
