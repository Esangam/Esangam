# Stage 1: Build with Maven + JDK 17
FROM maven:3.9.1-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Run with JDK 21
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy the built jar
COPY --from=build /app/target/quarkus-app/quarkus-run.jar ./quarkus-run.jar

EXPOSE 8080

CMD ["java", "-jar", "quarkus-run.jar"]
