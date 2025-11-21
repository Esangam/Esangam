# Stage 1: Build the Quarkus application with Maven + JDK 21
FROM maven:3.9.1-eclipse-temurin-21 AS build

WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the Quarkus application
RUN mvn package -DskipTests

# Stage 2: Run the Quarkus application with JDK 21
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/quarkus-app/quarkus-run.jar ./quarkus-run.jar

# Expose the port
EXPOSE 8080

# Command to run the app
CMD ["java", "-jar", "quarkus-run.jar"]
