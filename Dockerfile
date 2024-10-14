# Stage 1: Build the application using Maven
FROM maven:3.8.8-eclipse-temurin-21-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy the rest of the application source code
COPY src ./src

# Package the application (this will create the JAR file)
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:21-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage to the current stage
COPY --from=build /app/target/Reservation_app-0.0.1-SNAPSHOT.jar /app/application.jar

# Expose the port the app will run on
EXPOSE 8080

# Command to run the JAR file
CMD ["java", "-jar", "/app/application.jar"]
