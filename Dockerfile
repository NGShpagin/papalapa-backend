# syntax=docker/dockerfile:1

# Comments are provided throughout this file to help you get started.
# If you need more help, visit the Dockerfile reference guide at
# https://docs.docker.com/go/dockerfile-reference/

# Want to help us make this template better? Share your feedback here: https://forms.gle/ybq9Krt8jtBL3iCk7

################################################################################

# Create a stage for resolving and downloading dependencies.
#FROM eclipse-temurin:23-jdk-alpine as build
#COPY . /app
#WORKDIR /app
#COPY --chmod=0755 mvnw mvnw
#COPY --chmod=0755 pom.xml pom.xml
#COPY .mvn .mvn
#RUN apk update && apk add maven
##RUN mvn clean package -DskipTests
#RUN mvn clean package

#FROM maven:3.6.3 as build
#COPY . /build
#WORKDIR /build
#ENTRYPOINT ["mvn", "clean", "package"]
#RUN mvn clean install

FROM openjdk:23 as image
ADD https://storage.yandexcloud.net/cloud-certs/CA.pem /root/.postgresql/root.crt
RUN chmod "0644" /root/.postgresql/root.crt
COPY target/*.jar /app/app.jar
WORKDIR /app
EXPOSE 8280
ENTRYPOINT ["java", "-jar", "/app/app.jar"]