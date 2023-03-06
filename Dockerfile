FROM openjdk:17

WORKDIR /app

ADD build/libs/*.jar /app/xperf.jar

CMD java -jar /app/xperf.jar