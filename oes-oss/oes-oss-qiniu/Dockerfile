FROM adoptopenjdk:8-jdk-openj9
MAINTAINER chachae chachae@foxmail.com

COPY ./target/oes-oss-qiniu-1.0.jar /oes/oes-oss-qiniu-1.0.jar
ENTRYPOINT [ \
    "java", \
    "-XX:MetaspaceSize=128m", \
    "-XX:MaxMetaspaceSize=128m", \
    "-Xms256m", \
    "-Xmx512m", \
    "-Xmn128m", \
    "-Xss256k", \
    "-XX:SurvivorRatio=8", \
    "-XX:+UseConcMarkSweepGC", \
    "-Duser.timezone=GMT+08", \
    "-jar", \
    "/oes/oes-oss-qiniu-1.0.jar"\
]
