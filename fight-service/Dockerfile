
# Étape de construction pour le Player Service (maven build)
FROM maven:3.9.6-amazoncorretto-21 AS build
RUN mkdir -p /app
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn -f pom.xml clean package

# Étape pour l'exécution de l'API Player avec Amazon Corretto 21
FROM amazoncorretto:21.0.2-alpine3.19
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8085/tcp
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]
