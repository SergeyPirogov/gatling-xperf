FROM openjdk:17

WORKDIR /app

ADD build/libs/*.jar /app/xperf.jar

ENTRYPOINT  ["java", "-jar", "/app/xperf.jar", "--output-dir=/data"]