FROM eclipse-temurin:22-jdk

WORKDIR /app

COPY target/Reservation_app-0.0.1-SNAPSHOT.jar /app/application.jar

# Na którym porcie kontener bedzie nasłuchiwał
EXPOSE 8080

CMD ["java", "-jar", "application.jar"]

# RUN - okresla komendy do wykonania  podaczas budowania kontenera (komendy srodowiskowe tj apt get/ apt update itd)