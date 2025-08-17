package com.agentic_works.keycloak.config

data class ProvisionerConfig(
    val kafkaBrokers: String?,
    val kafkaTopic: String?
)