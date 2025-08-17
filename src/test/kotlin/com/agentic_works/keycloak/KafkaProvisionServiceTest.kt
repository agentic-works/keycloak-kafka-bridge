package com.agentic_works.keycloak

import com.agentic_works.keycloak.config.ProvisionerConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.component.mock.MockEndpoint
import org.apache.camel.test.junit5.CamelTestSupport
import org.junit.jupiter.api.Test
import org.keycloak.events.Event
import org.keycloak.events.EventType

class KafkaProvisionServiceTest : CamelTestSupport() {

    private val objectMapper = ObjectMapper()

    override fun createRouteBuilder(): RouteBuilder {
        return object : RouteBuilder() {
            override fun configure() {
                from("direct:start")
                    .to("mock:kafka")
            }
        }
    }

    @Test
    fun `test user registration event sends message to kafka`() {
        val mockKafkaEndpoint = getMockEndpoint("mock:kafka")
        mockKafkaEndpoint.expectedMessageCount(1)

        val config = ProvisionerConfig("mock-brokers", "mock-topic", "mock-url", "mock-realm", "mock-client", "mock-secret")
        val topicEventWriter = TopicEventWriter(config)
        topicEventWriter.start()

        val kafkaProvisionService = KafkaProvisionService(topicEventWriter)
        val tenantProvisionService = TenantProvisionService(kafkaProvisionService)

        val event = Event()
        event.type = EventType.REGISTER
        event.userId = "test-user"
        event.realmId = "test-tenant"

        // Replace the real kafka endpoint with the mock one
        context.routeController.getRoute("kafka-route")?.adviceWith(context, object : RouteBuilder() {
            override fun configure() {
                interceptSendToEndpoint("kafka:*")
                    .skipSendToOriginalEndpoint()
                    .to("mock:kafka")
            }
        })

        tenantProvisionService.onEvent(event)

        mockKafkaEndpoint.assertIsSatisfied()

        val receivedBody = mockKafkaEndpoint.receivedExchanges[0].getIn().getBody(String::class.java)
        val expectedBody = objectMapper.writeValueAsString(mapOf("username" to "test-user", "tenantId" to "test-tenant"))
        assertEquals(expectedBody, receivedBody)

        topicEventWriter.stop()
    }
}
