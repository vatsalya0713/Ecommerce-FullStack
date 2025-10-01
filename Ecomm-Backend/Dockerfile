FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

RUN apk add --no-cache maven

COPY . .

RUN mvn clean package -DskipTests

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "target/Ecomm-0.0.1-SNAPSHOT.jar"]
