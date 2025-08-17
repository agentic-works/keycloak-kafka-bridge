package com.agentic_works.keycloak.model

/**
 * Represents the event payload for a user provisioning action.
 * This data will be serialized to JSON and sent to the Kafka topic.
 *
 * @property userId The ID of the user being provisioned.
 * @property tenantId The ID of the tenant (realm) in which the event occurred.
 */
data class UserProvisioningEvent(
    val userId: String,
    val tenantId: String
)
