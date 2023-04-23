FROM openjdk:17-jdk-slim

RUN useradd -r -u 1000 devops
RUN mkdir -p /logs /opt/devops
RUN chown devops /opt/devops /logs

USER devops
WORKDIR /opt/devops

ARG VERSION=3.0.0
ARG JAR_FILE=build/libs/spring-petclinic-data-jdbc-${VERSION}.jar
COPY ${JAR_FILE} /opt/devops/petclinic.jar
COPY src/main/resources/application.properties application.properties
COPY src/main/resources/logback-spring.xml logback-spring.xml

ENTRYPOINT java ${JAVA_OPTIONS} ${JAVA_AGENT_OPTIONS} -jar petclinic.jar
