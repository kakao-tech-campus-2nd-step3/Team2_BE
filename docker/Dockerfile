# syntax=docker/dockerfile:1.4

# Build stage
FROM gradle:8.10-jdk21-jammy AS build

WORKDIR /app

# 먼저 의존성 파일만 복사하여 캐시를 활용한 의존성 설치
COPY --chown=gradle:gradle ../build.gradle.kts settings.gradle.kts ./
RUN gradle dependencies --no-daemon

# 이후 소스 파일 복사
COPY --chown=gradle:gradle .. ./

# 실제 빌드 단계
RUN gradle build -x test --no-daemon

# Runtime stage
FROM amazoncorretto:21.0.2-alpine3.19 AS runtime

WORKDIR /app

# 비루트 유저 및 그룹 생성
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# 빌드된 jar 파일을 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 비루트 유저 설정
USER appuser

# Healthcheck에서 curl 사용
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 타임존 설정
ENV TZ=Asia/Seoul

# 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
