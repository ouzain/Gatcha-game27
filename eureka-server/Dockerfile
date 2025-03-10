# Étape 1 : Build avec Maven
FROM maven:3.9.6-amazoncorretto-21 AS build

# Créer le répertoire de travail
RUN mkdir -p /app
WORKDIR /app

COPY pom.xml /app
COPY src /app/src

RUN mvn -f pom.xml clean package

# Étape 2 : Exécution avec Amazon Corretto 21
FROM amazoncorretto:21.0.2-alpine3.19

COPY --from=build /app/target/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka-server.jar

EXPOSE 8761/tcp

# Définir le point d'entrée pour exécuter le fichier JAR
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app/eureka-server.jar"]
