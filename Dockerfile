# Build
FROM maven:3-amazoncorretto-21 AS builder
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
COPY openapi.yml ./openapi.yml
RUN mvn -q -DskipTests clean package

# Run
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=builder /workspace/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
