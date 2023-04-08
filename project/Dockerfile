# Build stage
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY config ./config
RUN mvn clean checkstyle:check install

# Final stage
FROM openjdk:20
COPY --from=build /app/target/app-1.0.jar /docker/app.jar
WORKDIR /docker
CMD ["java", "-jar", "app.jar"]
