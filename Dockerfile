FROM openjdk:17-jdk-slim

RUN useradd -r -u 1000 devops
RUN mkdir /app /logs
RUN chown devops /app /logs

USER devops
WORKDIR /app

ARG VERSION=3.0.0
ARG JAR_FILE=build/libs/spring-petclinic-data-jdbc-${VERSION}.jar
COPY ${JAR_FILE} petclinic.jar
COPY src/main/resources/application.properties application.properties
COPY src/main/resources/logback-spring.xml logback-spring.xml

ENTRYPOINT java ${JAVA_OPTIONS} ${JAVA_AGENT_OPTIONS} -jar petclinic.jar
