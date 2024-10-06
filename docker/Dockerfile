# syntax=docker/dockerfile:1.4

# Build stage
FROM gradle:8.10-jdk21-jammy AS build

WORKDIR /app

# 먼저 의존성 파일만 복사하여 캐시를 활용한 의존성 설치
COPY --chown=gradle:gradle ../build.gradle.kts settings.gradle.kts ./
RUN gradle dependencies --no-daemon
COPY --chown=gradle:gradle .. ./

# 실제 빌드 단계
RUN gradle build -x test --no-daemon

# Runtime stage
FROM amazoncorretto:21.0.2-alpine3.19 AS runtime
ENV TZ=Asia/Seoul
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone

WORKDIR /app
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=build /app/build/libs/*.jar app.jar

USER appuser

HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-XX:MaxMetaspaceSize=128m", "-jar", "app.jar"]
