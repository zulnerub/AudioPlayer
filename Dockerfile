FROM openjdk:11
COPY target/AudioPlayer-2.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]