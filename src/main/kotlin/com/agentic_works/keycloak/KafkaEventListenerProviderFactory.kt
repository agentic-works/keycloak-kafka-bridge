package com.agentic_works.keycloak

import com.agentic_works.keycloak.config.ProvisionerConfig
import org.keycloak.Config
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.EventListenerProviderFactory
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory

class KafkaEventListenerProviderFactory : EventListenerProviderFactory {

    private lateinit var topicEventWriter: TopicEventWriter
    private lateinit var config: ProvisionerConfig

    override fun create(session: KeycloakSession): EventListenerProvider {
        val kafkaProvisionService = KafkaProvisionService(topicEventWriter)
        return KafkaEventListenerProvider(kafkaProvisionService)
    }

    override fun init(scope: Config.Scope) {
        val kafkaBrokers = scope.get("kafkaBrokers")
        val kafkaTopic = scope.get("kafkaTopic")
        config = ProvisionerConfig(kafkaBrokers, kafkaTopic)
        topicEventWriter = TopicEventWriter(config)
        topicEventWriter.start()
    }

    override fun postInit(factory: KeycloakSessionFactory) {
        // Not needed
    }

    override fun close() {
        topicEventWriter.stop()
    }

    override fun getId(): String {
        return "kafka-event-listener"
    }
}