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

FROM openjdk:23
LABEL authors="nikolajspagin"
ADD https://storage.yandexcloud.net/cloud-certs/CA.pem /home/user/.postgresql/root.crt
RUN chmod "0644" /home/user/.postgresql/root.crt
#WORKDIR /papalapa/back
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
