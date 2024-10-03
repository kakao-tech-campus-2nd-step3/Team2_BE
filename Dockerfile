# Syntax 버전 명시로 새로운 기능 활용
# syntax=docker/dockerfile:1.4

# Build stage
FROM gradle:8.10-jdk21-jammy AS build

WORKDIR /app

COPY --chown=gradle:gradle . ./

RUN gradle dependencies

COPY src ./src
RUN gradle build -x test --no-daemon

# Runtime stage
FROM amazoncorretto:21.0.2-alpine3.19 AS runtime

WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=build /app/build/libs/*.jar app.jar

USER appuser

HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -q --spider http://localhost:8080/actuator/health || exit 1

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "app.jar"]