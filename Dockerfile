FROM openjdk:11 as build
WORKDIR /workspace/app

COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./
COPY src src

RUN ./gradlew customFatJar -x test

FROM openjdk:11
WORKDIR /app

COPY --from=build /workspace/app/build/libs/xperf-1.0.jar /app/

ENTRYPOINT  ["java", "-jar", "/app/xperf-1.0.jar", "--output-dir=/data"]