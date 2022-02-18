FROM adoptopenjdk/openjdk11:latest

VOLUME /tmp
WORKDIR /build
COPY . /build
COPY build/libs/*.jar /app/base-project-spring-webflux.jar

ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app/base-project-spring-webflux.jar"]