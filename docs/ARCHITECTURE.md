# Architecture Overview

## Project Structure

The `keycloak-nextcloud-provisioner` project is structured to facilitate the integration of Keycloak with Nextcloud for user and tenant provisioning. The main components of the project are organized as follows:

```
keycloak-nextcloud-provisioner
├── build.gradle.kts          # Gradle build configuration
├── settings.gradle.kts       # Gradle settings
├── gradle.properties          # Gradle properties
├── README.md                 # Project documentation
├── Dockerfile                 # Docker image configuration
├── LICENSE                    # Licensing information
├── .env.example               # Example environment variables
├── config                     # Application configuration files
│   └── application.conf       # Configuration settings
├── src                        # Source code
│   ├── main                   # Main application code
│   │   ├── kotlin             # Kotlin source files
│   │   │   └── com
│   │   │       └── example
│   │   │           └── keycloak
│   │   │               ├── NextcloudEventListenerProvider.kt
│   │   │               ├── NextcloudEventListenerProviderFactory.kt
│   │   │               ├── NextcloudProvisionService.kt
│   │   │               ├── TenantProvisionService.kt
│   │   │               ├── KeycloakAdminClient.kt
│   │   │               ├── NextcloudClient.kt
│   │   │               ├── model
│   │   │               │   ├── NextcloudUserRequest.kt
│   │   │               │   └── TenantMetadata.kt
│   │   │               ├── config
│   │   │               │   └── ProvisionerConfig.kt
│   │   │               └── util
│   │   │                   └── Logging.kt
│   │   └── resources         # Resource files
│   │       ├── META-INF
│   │       │   └── services
│   │       │       └── org.keycloak.events.EventListenerProviderFactory
│   │       └── logback.xml   # Logging configuration
│   └── test                  # Test code
│       └── kotlin            # Kotlin test files
│           └── com
│               └── example
│                   └── keycloak
│                       ├── NextcloudProvisionServiceTest.kt
│                       └── NextcloudClientTest.kt
├── scripts                   # Scripts for running and building
│   ├── run-local.sh          # Local run script
│   └── build.sh              # Build script
└── docs                      # Documentation
    ├── ARCHITECTURE.md       # Architecture documentation
    └── NEXTCLOUD_API_NOTES.md # Nextcloud API notes
```

## Key Components

1. **Event Listener**: The `NextcloudEventListenerProvider` implements Keycloak's `EventListenerProvider` interface to listen for user registration and tenant creation events. This component is crucial for triggering the provisioning of Nextcloud accounts.

2. **Provisioning Services**:
   - `NextcloudProvisionService`: Contains the logic for creating Nextcloud accounts based on events received from Keycloak.
   - `TenantProvisionService`: Manages the creation of Nextcloud accounts for new tenants.

3. **API Clients**:
   - `KeycloakAdminClient`: Provides methods for interacting with the Keycloak Admin API, allowing for user and tenant management.
   - `NextcloudClient`: Facilitates communication with the Nextcloud API for user account creation and management.

4. **Configuration**: The `ProvisionerConfig` class holds configuration settings specific to the provisioning service, ensuring that the application can be easily configured for different environments.

5. **Logging**: The `Logging` utility provides centralized logging functionality, making it easier to track events and errors throughout the application.

## Workflow

1. **User Registration**: When a user registers in Keycloak, the `NextcloudEventListenerProvider` captures the event and invokes the `NextcloudProvisionService` to create a corresponding Nextcloud account.

2. **Tenant Creation**: Similarly, when a new tenant is created in Keycloak, the `TenantProvisionService` is triggered to provision the necessary Nextcloud accounts for that tenant.

3. **Configuration Management**: The application reads configuration settings from `application.conf`, allowing for flexible deployment across different environments.

This architecture ensures a clean separation of concerns, with distinct modules handling specific functionalities, making the project maintainable and scalable.