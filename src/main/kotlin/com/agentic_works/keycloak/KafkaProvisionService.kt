package com.agentic_works.keycloak

import org.keycloak.events.Event
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.EventType
import org.keycloak.events.EventListenerProviderFactory

import com.agentic_works.keycloak.model.UserProvisioningEvent

class KafkaProvisionService(private val topicEventWriter: TopicEventWriter) {

    fun provisionUser(userId: String, tenantId: String) {
        val event = UserProvisioningEvent(userId = userId, tenantId = tenantId)
        topicEventWriter.writeEvent(event)
    }

    fun provisionTenant(tenantId: String) {
        val event = UserProvisioningEvent(userId = "tenant-admin-$tenantId", tenantId = tenantId)
        topicEventWriter.writeEvent(event)
    }
}