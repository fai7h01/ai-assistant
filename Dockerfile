
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/ai-assistant-0.0.1-SNAPSHOT.jar ai-assistant-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "ai-assistant-0.0.1-SNAPSHOT.jar"]