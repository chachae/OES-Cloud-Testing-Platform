FROM adoptopenjdk:11-jdk-openj9

COPY oes-gateway-1.0.jar /oes-cloud/oes-gateway-1.0.jar
ENTRYPOINT [ \
    "java", \
    "-Duser.timezone=GMT+08", \
    "-jar", \
    "/oes-cloud/oes-gateway-1.0.jar"\
]
