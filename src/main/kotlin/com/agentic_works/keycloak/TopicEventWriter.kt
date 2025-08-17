package com.agentic_works.keycloak

import com.agentic_works.keycloak.config.ProvisionerConfig
import com.agentic_works.keycloak.model.NextcloudUserRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.camel.CamelContext
import org.apache.camel.impl.DefaultCamelContext

class TopicEventWriter(private val config: ProvisionerConfig) {

    private val camelContext: CamelContext = DefaultCamelContext()
    private val objectMapper = ObjectMapper()

    fun start() {
        camelContext.start()
    }

    fun stop() {
        camelContext.stop()
    }

    fun writeEvent(userRequest: NextcloudUserRequest) {
        val producer = camelContext.createProducerTemplate()
        val jsonBody = objectMapper.writeValueAsString(userRequest)
        producer.sendBody("kafka:${config.kafkaTopic}?brokers=${config.kafkaBrokers}", jsonBody)
    }
}