package com.agentic_works.keycloak.model

data class TenantMetadata(
    val tenantId: String,
    val tenantName: String,
    val nextcloudUrl: String,
    val nextcloudAdminUser: String,
    val nextcloudAdminPassword: String
)