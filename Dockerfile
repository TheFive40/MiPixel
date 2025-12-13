# Etapa 1: build
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY . .

# Darle permisos de ejecución al script mvnw
RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
