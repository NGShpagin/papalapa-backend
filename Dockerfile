FROM openjdk:23 as image
ADD https://storage.yandexcloud.net/cloud-certs/CA.pem /root/.postgresql/root.crt
RUN chmod "0644" /root/.postgresql/root.crt
COPY target/*.jar /app/app.jar
WORKDIR /app
EXPOSE 8280
ENTRYPOINT ["java", "-jar", "/app/app.jar"]