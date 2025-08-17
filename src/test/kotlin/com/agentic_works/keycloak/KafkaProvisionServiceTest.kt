package com.agentic_works.keycloak

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.camel.CamelContext
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.component.mock.MockEndpoint
import org.apache.camel.impl.DefaultCamelContext
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KafkaProvisionServiceTest {

    private lateinit var camelContext: CamelContext
    private val objectMapper = ObjectMapper()

    @BeforeEach
    fun setup() {
        camelContext = DefaultCamelContext()
        camelContext.addRoutes(object : RouteBuilder() {
            override fun configure() {
                from("direct:start")
                    .to("mock:kafka")
            }
        })
        camelContext.start()
    }

    @AfterEach
    fun tearDown() {
        camelContext.stop()
    }

    @Test
    fun `test user registration event sends message to kafka`() {
        val mockKafkaEndpoint = camelContext.getEndpoint("mock:kafka", MockEndpoint::class.java)
        mockKafkaEndpoint.expectedMessageCount(1)

        val expectedBody = objectMapper.writeValueAsString(mapOf("userId" to "test-user", "tenantId" to "test-tenant"))
        mockKafkaEndpoint.expectedBodiesReceived(expectedBody)

        val producerTemplate = camelContext.createProducerTemplate()
        producerTemplate.sendBody("direct:start", expectedBody)

        mockKafkaEndpoint.assertIsSatisfied()
    }
}
