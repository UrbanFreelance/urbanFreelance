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

COPY --from=builder /app/build/libs/api-gateway-0.0.1-SNAPSHOT.jar license_service.jar

RUN chown -R MyUser:MyGroup /app

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
  CMD wget --spider --quiet http://localhost:8080/actuator/health || exit 1

EXPOSE 8080
USER MyUser

ENTRYPOINT ["java","-jar","api-gateway.jar"]