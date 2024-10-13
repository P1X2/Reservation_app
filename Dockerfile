# Use a Maven build stage to compile the application and generate the JAR
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copy the pom.xml and project source
COPY pom.xml .
COPY src ./src

# Package the application (skip tests if needed)
RUN mvn clean package -DskipTests

# Now we have the built JAR in the target directory
# Use a JDK image to run the application
FROM eclipse-temurin:22-jdk

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/Reservation_app-0.0.1-SNAPSHOT.jar /app/application.jar

# Expose the port the app will run on
EXPOSE 8080

# Command to run the JAR file
CMD ["java", "-jar", "/app/application.jar"]
