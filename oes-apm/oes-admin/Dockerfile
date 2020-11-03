FROM adoptopenjdk:11-jdk-openj9
COPY oes-admin-1.0.jar /oes-cloud/oes-admin-1.0.jar
ENTRYPOINT [ \
    "java", \
    "-Duser.timezone=GMT+08", \
    "-jar", \
    "/oes-cloud/oes-admin-1.0.jar"\
]
