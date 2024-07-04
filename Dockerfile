FROM openjdk:17-jdk-alpine
WORKDIR /app

COPY . .
RUN ./mvnw clean package

ENTRYPOINT ["java", "-jar", "target/HappyPaws-1.0.jar"]