FROM openjdk:17-jdk-slim

RUN groupadd -r app && useradd -r -u 1000 -g app app
RUN mkdir /app /logs
RUN chown app:app /app /logs

USER app
WORKDIR /app

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/application.properties application.properties
COPY src/main/resources/logback-spring.xml logback-spring.xml

VOLUME /logs

EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Dlogging.file.path=/logs", "-jar", "app.jar"]
