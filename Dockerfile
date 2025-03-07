FROM maven:3.8.7-openjdk-18 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM openjdk:18-jdk-slim

COPY --from=build /home/app/target/putovanjeJava-0.0.1-SNAPSHOT.jar /app/putovanje.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/putovanje.jar"]