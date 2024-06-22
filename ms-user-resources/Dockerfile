# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS buildstage

# Set the working directory
WORKDIR /ms-user-resources

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package

RUN mvn verify sonar:sonar "-Dsonar.projectKey=ms-search-food-branch" "-Dsonar.projectName='ms-search-food-branch'" "-Dsonar.host.url=http://localhost:9000" "-Dsonar.token=sqa_5343de562a0b0b16289a0c81b75d70a2202a2ae5"

# Run stage
FROM eclipse-temurin:17 AS runstage

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=buildstage /ms-user-resources/target/ms-user-resources-0.0.1-SNAPSHOT.jar ms-user-resources.jar

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "ms-user-resources.jar"]
