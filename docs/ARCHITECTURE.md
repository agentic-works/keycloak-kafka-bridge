# Architecture Overview

This document outlines the architecture of the **Keycloak Kafka Bridge**, a provider designed to forward events from a Keycloak instance to a Kafka topic.

## Core Components

The project is a standard Maven-based Kotlin application, intended to be packaged as a JAR and deployed as a Keycloak provider.

1.  **`KafkaEventListenerProvider`**:
    *   This is the primary entry point for the provider.
    *   It implements Keycloak's `EventListenerProvider` interface.
    *   Its `onEvent` method is triggered by Keycloak whenever a registered event (e.g., user registration) occurs.
    *   It delegates the event handling to the `KafkaProvisionService`.

2.  **`KafkaProvisionService`**:
    *   This service acts as a bridge between the raw Keycloak event and the `TopicEventWriter`.
    *   It transforms the event data into a structured format.
    *   It calls the `TopicEventWriter` to send the structured data to Kafka.

3.  **`TopicEventWriter`**:
    *   This class is responsible for the actual communication with Kafka.
    *   It uses an **Apache Camel** `ProducerTemplate` to send messages.
    *   The Kafka endpoint (brokers and topic) is configured dynamically based on the settings provided to the factory.

4.  **`KafkaEventListenerProviderFactory`**:
    *   This factory class is responsible for creating and initializing the event listener.
    *   It implements Keycloak's `EventListenerProviderFactory` interface, which allows Keycloak to discover and load the provider.
    *   The `init` method reads the Kafka configuration (brokers, topic) from the Keycloak environment.
    *   It instantiates and manages the lifecycle of the `TopicEventWriter`.

5.  **Configuration (`ProvisionerConfig.kt`)**:
    *   A simple data class that holds the configuration for the provider, such as Kafka broker URLs and topic names.

## Event Flow

The data flows through the system as follows:

1.  An action occurs in Keycloak (e.g., a new user registers).
2.  Keycloak's event system fires an `Event`.
3.  The `KafkaEventListenerProvider` receives the `Event`.
4.  The provider calls the `KafkaProvisionService`, passing the relevant event details (e.g., user ID, realm ID).
5.  The `KafkaProvisionService` creates a data object and passes it to the `TopicEventWriter`.
6.  The `TopicEventWriter` serializes the data object to JSON and uses its Camel producer to send the message to the configured Kafka topic.

## Deployment Model

*   The project is built into a single JAR file using `mvn clean package`.
*   This JAR is then deployed to a Keycloak instance by placing it in the `/opt/keycloak/providers/` directory.
*   Keycloak automatically detects the provider via the `META-INF/services/org.keycloak.events.EventListenerProviderFactory` service file.
*   The provider is then enabled and configured in the Keycloak Admin Console for a specific realm.

This architecture ensures a clean separation of concerns and creates a loosely coupled bridge between Keycloak and a Kafka-based event-driven architecture.