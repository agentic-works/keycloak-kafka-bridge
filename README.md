# Keycloak Kafka Bridge

A Keycloak Event Listener provider, built with Kotlin and Maven, that forwards events to a Kafka topic using Apache Camel.

## Features

- Listens for Keycloak user and admin events.
- Publishes events as messages to a configurable Kafka topic.
- Uses Apache Camel for robust and extensible routing.
- Packaged as a standard JAR for easy deployment to Keycloak.

## Getting Started

### Prerequisites

- Java 17 or later
- Apache Maven 3.8 or later
- A running Keycloak instance
- A running Kafka broker (Kafka or RedPanda for instance)

### Build

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/agentic-works/keycloak-kafka-bridge.git
    cd keycloak-kafka-bridge
    ```

2.  **Build the provider JAR**:
    ```bash
    mvn clean package
    ```
    This will produce the provider JAR file in the `target/` directory (e.g., `keycloak-kafka-bridge-1.0.0-SNAPSHOT.jar`).

### Deployment

This provider is designed to be deployed to a Keycloak instance. The recommended method for development is to use Docker Compose with a volume mount.

1.  **Modify your `docker-compose.yml`**:
    Add a volume mount to your `keycloak` service definition to mount the provider JAR into the Keycloak container.

    ```yaml
    services:
      keycloak:
        image: quay.io/keycloak/keycloak:latest
        # ... other settings
        volumes:
          # ... other volumes
          - /path/to/your/keycloak-kafka-bridge/target/keycloak-kafka-bridge-1.0.0-SNAPSHOT.jar:/opt/keycloak/providers/keycloak-kafka-bridge.jar
    ```
    Replace `/path/to/your/` with the actual path to your project directory.

2.  **Start Keycloak**:
    ```bash
    docker-compose up -d
    ```

### Configuration

1.  **Enable the Event Listener**:
    *   Log in to the Keycloak Admin Console.
    *   Navigate to **Realm Settings** -> **Events**.
    *   In the **Event Listeners** tab, add `kafka-event-listener` to the list of enabled event listeners.
    *   Save the changes.

2.  **Configure Kafka Connection**:
    The provider is configured through the Keycloak admin console. When you enable the provider, you can set the following environment variables or system properties in your Keycloak deployment:
    *   `KC_SPI_EVENTS_LISTENER_KAFKA_EVENT_LISTENER_KAFKA_BROKERS`: The comma-separated list of Kafka brokers (e.g., `localhost:9092`).
    *   `KC_SPI_EVENTS_LISTENER_KAFKA_EVENT_LISTENER_KAFKA_TOPIC`: The Kafka topic to which events will be published (e.g., `keycloak-events`).

## License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for more details.
