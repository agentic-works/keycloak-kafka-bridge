package com.agentic_works.keycloak

import org.keycloak.events.Event
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.EventType
import org.keycloak.events.EventListenerProviderFactory

import com.agentic_works.keycloak.model.NextcloudUserRequest

class KafkaProvisionService(private val topicEventWriter: TopicEventWriter) {

    fun provisionUser(userId: String, tenantId: String) {
        val userRequest = NextcloudUserRequest(username = userId, tenantId = tenantId)
        topicEventWriter.writeEvent(userRequest)
    }

    fun provisionTenant(tenantId: String) {
        val userRequest = NextcloudUserRequest(username = "tenant-admin-$tenantId", tenantId = tenantId)
        topicEventWriter.writeEvent(userRequest)
    }
}

class NextcloudEventListenerProvider(private val provisionService: NextcloudProvisionService) : EventListenerProvider {

    override fun onEvent(event: Event) {
        when (event.type) {
            EventType.REGISTER -> {
                provisionService.provisionNextcloudAccount(event.userId, event.tenantId)
            }
            EventType.CREATE_TENANT -> {
                provisionService.provisionNextcloudTenant(event.tenantId)
            }
            else -> {
                // Handle other events if necessary
            }
        }
    }

    override fun close() {
        // Cleanup resources if needed
    }
}

class NextcloudEventListenerProviderFactory : EventListenerProviderFactory {

    override fun create(eventListenerProvider: EventListenerProvider): EventListenerProvider {
        val provisionService = NextcloudProvisionService()
        return NextcloudEventListenerProvider(provisionService)
    }

    override fun getId(): String {
        return "nextcloud-event-listener"
    }
}