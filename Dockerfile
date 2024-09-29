FROM gradle:8.10-jdk21 AS build

COPY --chown=gradle:gradle . /home/gradle/project

WORKDIR /home/gradle/project

RUN gradle build -x test

FROM amd64/amazoncorretto:21

COPY --from=build /home/gradle/project/build/libs/*.jar /app.jar

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/app.jar"]





