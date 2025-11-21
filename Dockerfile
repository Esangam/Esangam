# Stage 1: Build the Quarkus application with Maven + JDK 17
FROM maven:3.9.1-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and resources
COPY src ./src
COPY src/main/resources ./src/main/resources

# Build the Quarkus app as a runnable jar (fat jar)
RUN mvn package -DskipTests -Dquarkus.package.type=uber-jar

# Stage 2: Run the Quarkus application with JDK 21
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy the built jar
COPY --from=build /app/target/*-runner.jar ./app.jar

# Expose the port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
