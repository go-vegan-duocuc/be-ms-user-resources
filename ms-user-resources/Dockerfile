# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS buildstage

# Set the working directory
WORKDIR /ms-user-resources

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17 AS runstage

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=buildstage /ms-user-resources/target/ms-user-resources-0.0.1-SNAPSHOT.jar ms-user-resources.jar

# Copy the Firebase service account key
COPY src/main/resources/serviceAccountKey.json /app/serviceAccountKey.json

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "ms-user-resources.jar"]