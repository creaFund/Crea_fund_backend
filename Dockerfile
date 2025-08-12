# Étape 1 : Installer Maven sur OpenJDK 22
FROM openjdk:22-jdk-slim AS build

# Installer Maven manuellement
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Étape 1 : Build de l'application avec Maven
FROM maven:3.9.6-eclipse-temurin-22 AS build

WORKDIR /app

# Copier uniquement les fichiers Maven d'abord (pour cache build)
COPY creafund-api/pom.xml .
COPY creafund-api/.mvn .mvn
COPY creafund-api/mvnw .

# Télécharger les dépendances pour accélérer le build
RUN ./mvnw dependency:go-offline

# Copier le reste du code
COPY creafund-api/src ./src

# Compiler le projet sans exécuter les tests
RUN ./mvnw clean package -DskipTests

# Étape 2 : Image finale avec OpenJDK 22
FROM openjdk:22-jdk-slim

WORKDIR /app

# Copier le jar depuis l'étape de build
COPY --from=build /app/target/*.jar backend.jar

# Exposer le port utilisé par Render
EXPOSE 8080

# Lancer l'application en prenant en compte la variable PORT de Render
ENTRYPOINT ["sh", "-c", "java -jar backend.jar --server.port=${PORT:-8080}"]

