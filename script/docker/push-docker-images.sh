#!/usr/bin/env bash
# 统一镜像打包

cd oes-apm
docker build -t oes-apm .
cd ../

cd oes-gateway
docker build -t oes-gateway .
cd ../

cd oes-auth
docker build -t oes-auth .
cd ../

cd oes-server-system
docker build -t oes-server-system .
cd ../

cd oes-server-exam-online
docker build -t oes-server-exam-online .
cd ../

cd oes-server-exam-basic
docker build -t oes-server-exam-basic .
cd ../

cd oes-ai
docker build -t oes-ai .
cd ../

cd oes-oss-qiniu
docker build -t oes-oss-qiniu .
cd ../
