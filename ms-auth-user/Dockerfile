# Dockerfile
FROM maven:3.9.6-eclipse-temurin-17 AS buildstage

WORKDIR /ms-auth-user

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy AS runstage

WORKDIR /app

COPY --from=buildstage /ms-auth-user/target/ms-auth-user-0.0.1-SNAPSHOT.jar ms-auth-user.jar

ENTRYPOINT ["java", "-jar", "ms-auth-user.jar"]