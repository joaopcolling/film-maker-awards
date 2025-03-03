FROM openjdk:17-jdk-alpine
WORKDIR /app
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} /app/app.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.war", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]