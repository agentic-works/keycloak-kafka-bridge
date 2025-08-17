package com.agentic_works.keycloak.config

data class ProvisionerConfig(
    val kafkaBrokers: String,
    val kafkaTopic: String,
    val keycloakBaseUrl: String,
    val keycloakRealm: String,
    val keycloakClientId: String,
    val keycloakClientSecret: String
)