# https://spring.io/guides/gs/spring-boot-docker
FROM openjdk:22-jdk
# RUN addgroup -S spring && adduser -S spring -G spring
# USER spring:spring
ARG JAR_FILE=build/libs/\*.jar
COPY ${JAR_FILE} app.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]