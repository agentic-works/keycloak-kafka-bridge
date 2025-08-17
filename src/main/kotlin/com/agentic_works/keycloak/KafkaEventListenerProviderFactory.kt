package com.agentic_works.keycloak

import com.agentic_works.keycloak.config.ProvisionerConfig
import org.keycloak.Config
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory

class KafkaEventListenerProviderFactory : EventListenerProviderFactory {

    private lateinit var topicEventWriter: TopicEventWriter

    override fun create(session: KeycloakSession): EventListenerProvider {
        return TenantProvisionService(KafkaProvisionService(topicEventWriter))
    }

    override fun init(config: Config.Scope) {
        val provisionerConfig = ProvisionerConfig(
            kafkaBrokers = config.get("kafkaBrokers", "localhost:9092"),
            kafkaTopic = config.get("kafkaTopic", "keycloak-events"),
            keycloakBaseUrl = config.get("keycloakBaseUrl"),
            keycloakRealm = config.get("keycloakRealm"),
            keycloakClientId = config.get("keycloakClientId"),
            keycloakClientSecret = config.get("keycloakClientSecret")
        )
        topicEventWriter = TopicEventWriter(provisionerConfig)
        topicEventWriter.start()
    }

    override fun postInit(factory: KeycloakSessionFactory) {
    }

    override fun close() {
        topicEventWriter.stop()
    }

    override fun getId(): String {
        return "kafka-event-listener"
    }
}