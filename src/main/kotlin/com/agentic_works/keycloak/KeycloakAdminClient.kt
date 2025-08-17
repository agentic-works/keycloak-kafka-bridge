package com.agentic_works.keycloak

import org.keycloak.events.Event
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.EventListenerProviderFactory
import org.keycloak.models.KeycloakSession
import org.keycloak.models.RealmModel

class KeycloakAdminClient(private val session: KeycloakSession) {

    fun createNextcloudUser(username: String, email: String) {
        // Logic to create a Nextcloud user using the Nextcloud API
    }

    fun createNextcloudTenant(tenantId: String) {
        // Logic to create a Nextcloud tenant using the Nextcloud API
    }
}

class NextcloudEventListenerProvider(private val session: KeycloakSession) : EventListenerProvider {

    private val keycloakAdminClient = KeycloakAdminClient(session)

    override fun onEvent(event: Event) {
        when (event.type) {
            Event.Type.REGISTER -> {
                val username = event.details["username"] ?: return
                val email = event.details["email"] ?: return
                keycloakAdminClient.createNextcloudUser(username, email)
            }
            Event.Type.CREATE_REALM -> {
                val realmName = event.details["realm"] ?: return
                keycloakAdminClient.createNextcloudTenant(realmName)
            }
            else -> {
                // Handle other events if necessary
            }
        }
    }

    override fun close() {
        // Clean up resources if needed
    }
}

class NextcloudEventListenerProviderFactory : EventListenerProviderFactory {

    override fun create(session: KeycloakSession): EventListenerProvider {
        return NextcloudEventListenerProvider(session)
    }

    override fun getId(): String {
        return "nextcloud-event-listener"
    }
}