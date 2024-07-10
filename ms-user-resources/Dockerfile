# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS buildstage

WORKDIR /ms-user-resources

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-alpine AS runstage

WORKDIR /app

COPY --from=buildstage /ms-user-resources/target/ms-user-resources-0.0.1-SNAPSHOT.jar app.jar

# No copiamos el serviceAccountKey.json aquí

ENTRYPOINT ["java", "-jar", "/app/app.jar"]