FROM --platform=linux/amd64 openjdk:12.0.2

EXPOSE 8080

ADD ./build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar", "-Dspring.profiles.active=docker"]
