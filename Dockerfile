FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN ./mvnw -DskipTests clean package || mvn -DskipTests clean package

FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/target/DriveX-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]