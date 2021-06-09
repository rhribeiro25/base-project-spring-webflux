FROM openjdk:11-jdk-alpine

WORKDIR /app

CMD java  -Duser.timezone="America/Sao_Paulo" \
          -jar /app/base-project-spring-webflux-0.0.1-SNAPSHOT.jar
EXPOSE 9090