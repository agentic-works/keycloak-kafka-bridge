package com.agentic_works.keycloak

import org.keycloak.events.Event
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.EventType
import org.keycloak.events.EventListenerProviderFactory

class KafkaEventListenerProvider(
    private val kafkaProvisionService: KafkaProvisionService
) : EventListenerProvider {

    override fun onEvent(event: Event) {
        when (event.type) {
            EventType.REGISTER -> {
                kafkaProvisionService.provisionUser(event.userId, event.realmId)
            }
            // Handle other events if necessary
            else -> {}
        }
    }

    override fun close() {
        // Clean up resources if needed
    }
}

class NextcloudEventListenerProviderFactory : EventListenerProviderFactory {
    override fun create(eventListenerProvider: EventListenerProvider): EventListenerProvider {
        val provisionService = NextcloudProvisionService() // Initialize with necessary dependencies
        return NextcloudEventListenerProvider(provisionService)
    }

    override fun getId(): String {
        return "nextcloud-event-listener"
    }
}