package com.agentic_works.keycloak.model

data class NextcloudUserRequest(
    val username: String,
    val password: String,
    val email: String,
    val displayName: String? = null,
    val groups: List<String>? = null
)