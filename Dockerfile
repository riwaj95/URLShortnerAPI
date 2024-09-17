FROM openjdk:17-jdk
COPY target/Task-0.0.1-SNAPSHOT.jar Task-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "Task-0.0.1-SNAPSHOT.jar"]