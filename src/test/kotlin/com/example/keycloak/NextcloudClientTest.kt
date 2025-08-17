package com.agentic_works.keycloak

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class NextcloudClientTest {

    private val nextcloudClient = mock(NextcloudClient::class.java)

    @Test
    fun `test create user`() {
        val username = "testuser"
        val password = "password"
        val expectedResponse = "User created successfully"

        `when`(nextcloudClient.createUser(username, password)).thenReturn(expectedResponse)

        val actualResponse = nextcloudClient.createUser(username, password)

        assertEquals(expectedResponse, actualResponse)
        verify(nextcloudClient).createUser(username, password)
    }

    @Test
    fun `test get user`() {
        val username = "testuser"
        val expectedUser = NextcloudUserRequest(username)

        `when`(nextcloudClient.getUser(username)).thenReturn(expectedUser)

        val actualUser = nextcloudClient.getUser(username)

        assertEquals(expectedUser, actualUser)
        verify(nextcloudClient).getUser(username)
    }

    @Test
    fun `test delete user`() {
        val username = "testuser"
        val expectedResponse = "User deleted successfully"

        `when`(nextcloudClient.deleteUser(username)).thenReturn(expectedResponse)

        val actualResponse = nextcloudClient.deleteUser(username)

        assertEquals(expectedResponse, actualResponse)
        verify(nextcloudClient).deleteUser(username)
    }
}