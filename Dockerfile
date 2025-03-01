# БД PostgresQL
#FROM postgres:latest
#LABEL authors="nikolajspagin"
#ENV POSTGRES_DB papalapa
#ENV POSTGRES_USER user
#ENV POSTGRES_PASSWORD secret

#FROM openjdk:latest
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

#FROM ubuntu:latest
#RUN apt-get update -y
#RUN apt-get install -y nginx
#ENTRYPOINT ["nginx", "-g", "daemon off;"]

#
# Build stage
#
#FROM maven:4.0.0-alpine AS build
#WORKDIR /build
#COPY . /app
#COPY pom.xml pom.xml
#RUN mvn clean package

#COPY pom.xml /tmp/pom.xml
#RUN mvn -B -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
#COPY . /app
#WORKDIR /app
#RUN mvn clean package
#
# Package stage
#
#FROM openjdk:23
#LABEL authors="nikolajspagin"
#ADD https://storage.yandexcloud.net/cloud-certs/CA.pem /root/.postgresql/root.crt
#RUN chmod "0644" /root/.postgresql/root.crt
#WORKDIR /home/user/papalapa/backend
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} /opt/app/app.jar
#WORKDIR /opt/app
#ENTRYPOINT ["java","-jar","app.jar"]

#FROM maven:3.9.9 AS build
#COPY . /app/backend
#COPY pom.xml /app/backend/pom.xml
#WORKDIR /app/backend
#RUN mvn clean package -DskipTests
#FROM openjdk:23
#LABEL authors="nikolajspagin"
#ARG JAR_FILE=/app/backend/target/*.jar
#COPY --from=build ${JAR_FILE} /opt/app/app.jar
#WORKDIR /opt/app
#CMD ["java", "-jar", "/opt/app/app.jar"]

FROM maven:3.6.3 AS build
RUN mkdir /app
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM openjdk:23
ADD https://storage.yandexcloud.net/cloud-certs/CA.pem /root/.postgresql/root.crt
RUN chmod "0644" /root/.postgresql/root.crt
RUN mkdir /app
COPY ./target/papalapa-0.0.1-SNAPSHOT.jar /app/papalapa-0.0.1-SNAPSHOT.jar
WORKDIR /app
CMD "java" "-jar" "papalapa-0.0.1-SNAPSHOT.jar"