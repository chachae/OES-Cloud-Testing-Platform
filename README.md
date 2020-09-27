<p align="center" >
    <img src="./images/oes-cloud-logo-2.png" width="45%"></img>
</p>
<p align="center">
  <strong>高性能，易拓展，分布式多场景在线考试平台</strong>
</p>

<p align="center">
    <a target="_blank" href="https://github.com/chachae/OES-Cloud-Testing-Platform/blob/master/LICENSE">
        <img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license"/>
    </a>
    <a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
        <img src="https://img.shields.io/badge/JDK-8+-green.svg"/>
    </a>
    <a href="https://app.codacy.com/project/badge/Grade/0654dc357f844b75946f2d06b56df5c0)](https://www.codacy.com/manual/chachae/OES-Cloud-Testing-Platform/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=chachae/OES-Cloud-Testing-Platform&amp;utm_campaign=Badge_Grade">
        <img src="https://api.codacy.com/project/badge/Grade/4367ffad5b434b7e8078b3a68cc6398d"/>
    </a>
        <img src="https://tokei.rs/b1/github/chachae/OES-Cloud-Testing-Platform?category=lines"/>
        <img src="https://img.shields.io/badge/SpringCloudAlibaba-2.2.1.RELEASE-blueviolet.svg?style=flat-square"/>
</p>
<br/>

--------------------------------------------------------------------------------

# Introduction

This is my undergraduate graduation project, based on a distributed online examination platform developed by <strong>Spring Cloud</strong>. Based on the distributed architecture, it integrates a variety of high-performance components, such as <strong>Netty, Redis</strong> and other outstanding open-source projects, combined with the monitoring management platform and gateway request in-depth monitoring, as well as multiple design patterns and clear business processes.

--------------------------------------------------------------------------------

# Modules

* oes-ai : Distributed image recognition and biological detection system.
* oes-apm : Distributed system monitoring module, currently integrated with SpringBoot-Admin.
* oes-auth : Microservice authentication server, using oauth2 to achieve unified authentication and authorization.
* oes-common : System common components and spring boot starter integration module.
* oes-gateway : Microservice gateway with extremely high performance.
* oes-oss : Object storage service base on QiNiu.
* oes-server : System business microservice integration module.
* oes-tx-manager : Distributed transaction management base on TX-Manager.
* oes-server-system : System business microservices, providing basic system capabilities.
* oes-server-exam-basic : Online examination business microservices, providing basic system capabilities.
* oes-server-exam-online : Online examination microservices, provide online examination services, and integrate high-performance components to provide candidates with a Wenting examination environment.

--------------------------------------------------------------------------------

# Features

* The authentication server is separated from the resource server to facilitate access to your own microservice system.
* Gateway current limit, fault tolerance, degradation, gateway blacklist limit and gateway log.
* OAuth2.0 authentication and authorization, independent SSO single sign-on.
* Highly packaged Baidu, Alicard OCR optical recognition, face contrast recognition and other configurable solutions.
* Fully automated control of examination process and data processing scheme of message queue.
* Subjective similarity calculation and solution similarity streaming calculation solution.
* Multi-level modularization and externalization, configurable architecture design, integration of multiple design modes.
* Fully automated control of the examination process and message queue data processing program. Online examination.
* Identity verification, data desensitization, full-end monitoring of examination behavior and abnormal behavior log tracking and recording.

--------------------------------------------------------------------------------

# Prerequisite

* JDK 1.8+
* Mysql
* Redis
* MongoDB

--------------------------------------------------------------------------------

# Third-party module

* Nacos（Service config and registry）
* Elasticsearch
* Logstash
* Kibana
* Prometheus

--------------------------------------------------------------------------------

# Document & Website
In preparation.
