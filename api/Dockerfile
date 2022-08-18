FROM openjdk:11
ARG JAR_FILE=build/libs/api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} api.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=local", "/api.jar"]