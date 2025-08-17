package com.agentic_works.keycloak

import org.keycloak.events.Event
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.EventListenerProviderFactory

class TenantProvisionService(private val kafkaProvisionService: KafkaProvisionService) : EventListenerProvider {

    override fun onEvent(event: Event) {
        when (event.type) {
            Event.Type.REGISTER -> kafkaProvisionService.provisionUser(event.userId, event.realmId)
            // Assuming you have a custom event for tenant creation
            // Event.Type.CREATE_TENANT -> kafkaProvisionService.provisionTenant(event.realmId)
            else -> {}
        }
    }

    private fun handleUserRegistration(event: Event) {
        // Logic to create a Nextcloud account for the registered user
    }

    private fun handleTenantCreation(event: Event) {
        // Logic to create a Nextcloud account for the new tenant
    }

    override fun close() {
        // Cleanup resources if needed
    }
}