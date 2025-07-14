# https://spring.io/guides/gs/spring-boot-docker
FROM openjdk:16-jdk-alpine
ARG JAR_FILE=build/lib/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]