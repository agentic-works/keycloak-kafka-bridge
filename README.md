<<<<<<< HEAD
# Keycloak Nextcloud Provisioner

This project implements a Keycloak EventListenerProvider to listen for user registration and tenant creation events, automatically provisioning Nextcloud accounts for each new user and tenant.

## Project Structure

- **build.gradle.kts**: Gradle build configuration for the Kotlin project.
- **settings.gradle.kts**: Defines settings for the Gradle build, including project name and modules.
- **gradle.properties**: Contains properties for the Gradle build, such as versioning and configuration settings.
- **README.md**: Documentation for the project, including setup instructions and usage guidelines.
- **Dockerfile**: Instructions for building a Docker image for the project.
- **LICENSE**: Licensing information for the project.
- **.env.example**: Example of environment variables needed for the project.
- **config/application.conf**: Configuration settings for the application, such as database connections and API endpoints.
- **src/**: Contains the main Kotlin source code and resources.
- **scripts/**: Contains scripts for running and building the project.
- **docs/**: Documentation related to the project architecture and Nextcloud API.

## Setup Instructions

1. **Clone the Repository**: 
   ```
   git clone <repository-url>
   cd keycloak-nextcloud-provisioner
   ```

2. **Configure Environment Variables**: 
   Copy `.env.example` to `.env` and fill in the required values.

3. **Build the Project**: 
   Use the following command to build the project:
   ```
   ./gradlew build
   ```

4. **Run the Application**: 
   To run the application locally, use:
   ```
   ./scripts/run-local.sh
   ```

5. **Docker Setup**: 
   To build a Docker image, run:
   ```
   docker build -t keycloak-nextcloud-provisioner .
   ```

## Usage

The application listens for Keycloak events and provisions Nextcloud accounts automatically. Ensure that Keycloak is configured to send events to this application.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.

## Documentation

For more detailed information on the architecture and API interactions, refer to the documentation in the `docs/` directory.
=======
# keycloak-kafka-bridge
A Keycloak Event Listener provider, built with Kotlin and Maven, that forwards events to a Kafka topic using Apache Camel.
>>>>>>> origin/main
