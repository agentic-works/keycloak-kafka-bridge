FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and build files
COPY gradlew gradlew
COPY gradlew.bat gradlew.bat
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
COPY gradle.properties gradle.properties

# Copy the application source code
COPY src src

# Copy the configuration files
COPY config config

# Copy the scripts
COPY scripts scripts

# Copy the README and LICENSE files
COPY README.md README.md
COPY LICENSE LICENSE

# Copy the .env.example file
COPY .env.example .env.example

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build -x test

# Set the entry point for the Docker container
ENTRYPOINT ["java", "-jar", "build/libs/keycloak-nextcloud-provisioner.jar"] 

# Expose the application port (if applicable)
EXPOSE 8080