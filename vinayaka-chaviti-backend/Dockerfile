# Multi-stage build: compile with Maven, run on a slim JRE.
# Deploy this backend to a host that supports long-running containers
# (Netlify only hosts static frontends) — e.g. Railway, Render, or Fly.io.

FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
