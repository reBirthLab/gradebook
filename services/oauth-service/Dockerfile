FROM openjdk:10.0.1

MAINTAINER anastasiy.tovstik@gmail.com

ENV APP_NAME=oauth-service

COPY . /tmp/services

ENV GRADLE_OPTS="-Xmx128m -Dorg.gradle.daemon=false"

RUN chmod -R 777 /tmp/services && cd /tmp/services/${APP_NAME} && ../gradlew clean build \
    && mkdir /deployments && mv build/libs/* /deployments/${APP_NAME}.jar

RUN useradd user -g root && chmod -R 755 /deployments && chown -R user:root /deployments \
    && rm -r /tmp/services ~/.gradle

USER user:root

ENTRYPOINT java "-Dspring.profiles.active=${ACTIVE_PROFILE}" \
    "-Dspring.datasource.url=${DATABASE_URL}/${DATABASE_NAME}" \
    "-Dspring.datasource.username=${DATABASE_USER}" \
    "-Dspring.datasource.password=${DATABASE_PASSWORD}" \
    -jar "/deployments/${APP_NAME}.jar"

EXPOSE 8080