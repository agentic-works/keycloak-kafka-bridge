package com.agentic_works.keycloak

import org.keycloak.events.Event
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.EventType
import org.keycloak.events.admin.AdminEvent

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

    override fun onEvent(event: AdminEvent?, includeRepresentation: Boolean) {
        // Not implemented
    }

    override fun close() {
        // Clean up resources if needed
    }
}

