# Build Phase
FROM gradle:jdk25-alpine AS builder
LABEL authors="SAISHIVA"
WORKDIR /app
COPY build.gradle settings.gradle ./
RUN gradle dependencies --no-daemon || true
COPY src ./src
RUN gradle bootJar --no-daemon -x test

#RUn phase
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

RUN apk add --no-cache wget && \
    addgroup -S MyGroup && \
    adduser -S MyUser -G MyGroup

COPY --from=builder /app/build/libs/config-service-0.0.1-SNAPSHOT.jar license_service.jar

RUN chown -R MyUser:MyGroup /app

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
  CMD wget --spider --quiet http://localhost:8070/actuator/health || exit 1

EXPOSE 8070
USER MyUser

ENTRYPOINT ["java","-jar","config-service.jar"]