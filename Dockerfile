FROM amazoncorretto:17-alpine
WORKDIR /moyeorak
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
COPY build/libs/content-service-0.0.1-SNAPSHOT.jar content-service.jar
RUN chown appuser:appgroup /moyeorak
USER appuser
ENTRYPOINT ["java", "-jar", "content-service.jar"]