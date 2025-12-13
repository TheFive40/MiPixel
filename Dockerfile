FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY . .

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

RUN ./mvnw clean package -Dmaven.test.skip=true

EXPOSE 8080
CMD ["java", "-jar", "target/*.jar"]
