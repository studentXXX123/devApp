FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY gameofthrones-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "/gameofthrones-0.0.1-SNAPSHOT.jar"]