# Etapa 1: construir el JAR con Maven
FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copiamos pom.xml primero para usar caché
COPY pom.xml .

# Copiamos el código fuente
COPY src ./src

# Construimos el JAR (igual que haces en local)
RUN ./mvnw -DskipTests clean package || mvn -DskipTests clean package

# Etapa 2: imagen final ligera para ejecutar el JAR
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copiamos el JAR construido desde la etapa anterior
COPY --from=build /app/target/DriveX-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]