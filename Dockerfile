FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY target/*.jar oop.jar
ENTRYPOINT ["java","-jar","/oop.jar"]
EXPOSE 8080