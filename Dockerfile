FROM openjdk:11
COPY target/AudioPlayer-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]