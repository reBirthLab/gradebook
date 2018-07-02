FROM openjdk:10.0.1

MAINTAINER anastasiy.tovstik@gmail.com

ENV APP_NAME=gradebook-service

COPY . /tmp/services

RUN chmod -R 777 /tmp/services && cd /tmp/services/${APP_NAME} && ../gradlew clean build && \
    && mv build/libs/* ~/${APP_NAME}.jar
    && cd / && rm -r /tmp/services ~/.gradle

ENTRYPOINT java "-Dspring.profiles.active=${ACTIVE_PROFILE}" \
    "-Dspring.datasource.url=${DATABASE_URL}" \
    -jar "/root/${APP_NAME}.jar"

EXPOSE 8081