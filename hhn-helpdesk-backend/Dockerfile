FROM openjdk:17-jdk as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean install -DskipTests=true

FROM alpine:edge
MAINTAINER rz.hs-heilbronn.de
RUN apk add --no-cache openjdk17

WORKDIR /deployments/
COPY --from=build /workspace/app/target/hhn-helpdesk-backend-1.0.0-SNAPSHOT.jar /deployments/

ENTRYPOINT ["java", "-jar", "/deployments/hhn-helpdesk-backend-1.0.0-SNAPSHOT.jar"]

