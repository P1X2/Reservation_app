FROM eclipse-temurin:22-jdk

WORKDIR /app

COPY target/Reservation_app-0.0.1-SNAPSHOT.jar /app/application.jar

EXPOSE 8080

CMD ["java", "-jar", "application.jar"]
