# Zaciągnięcie base img, na podstawie ktrego robiony jest image roboczy
FROM eclipse-temurin:22-jdk

# Okreslenie katalogu roboczego w kontenerze (wszystkie komendy realtive do WORKDIR-a) w kont
WORKDIR /app

# Kopiowanie plikow z lok do container; jest jeszcze ADD, który kopiuje pliki, foldery albo url(?)
COPY ../target/Reservation_app-0.0.1-SNAPSHOT.jar /app/application.jar

# Na którym porcie kontener bedzie nasłuchiwał
EXPOSE 8080

# CMD definiuje jakie komendy będą odpalone przy włączaniu kontenera
CMD ["java", "-jar", "application.jar"]

# Z ciekawostek:
# RUN - okresla komendy do wykonania  podaczas budowania kontenera (komendy srodowiskowe tj apt get/ apt update itd)
#TODO VOLUME - enables mounting host machine dir to the container - np. DB po wyłączeniu zachowuje dane