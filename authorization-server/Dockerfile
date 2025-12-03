# Etapa de build: compila el JAR dentro del contenedor
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B -DskipTests clean package

# Etapa de runtime: imagen ligera solo con el JAR
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 9000
ENTRYPOINT ["java","-jar","/app/app.jar"]
