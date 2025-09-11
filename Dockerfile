# build stage
FROM eclipse-temurin:17 AS builder
WORKDIR /workspace
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar -x test

# run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /workspace/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]

# MSA Versioin
FROM amazoncorretto:17-alpine
WORKDIR /moyeorak
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
COPY build/libs/content-service-0.0.1-SNAPSHOT.jar content-service.jar
RUN chown appuser:appgroup /moyeorak
USER appuser
ENTRYPOINT ["java", "-jar", "content-service.jar"]
