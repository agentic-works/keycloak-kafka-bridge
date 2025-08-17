import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import com.agentic_works.keycloak.NextcloudProvisionService
import com.agentic_works.keycloak.KeycloakAdminClient
import com.agentic_works.keycloak.NextcloudClient
import com.agentic_works.keycloak.model.NextcloudUserRequest

class NextcloudProvisionServiceTest {

    private lateinit var nextcloudProvisionService: NextcloudProvisionService
    private lateinit var keycloakAdminClient: KeycloakAdminClient
    private lateinit var nextcloudClient: NextcloudClient

    @BeforeEach
    fun setUp() {
        keycloakAdminClient = mock(KeycloakAdminClient::class.java)
        nextcloudClient = mock(NextcloudClient::class.java)
        nextcloudProvisionService = NextcloudProvisionService(keycloakAdminClient, nextcloudClient)
    }

    @Test
    fun `should create Nextcloud account when user registers`() {
        val userId = "test-user-id"
        val nextcloudUserRequest = NextcloudUserRequest(userId, "test@example.com", "password")

        `when`(keycloakAdminClient.getUserEmail(userId)).thenReturn("test@example.com")
        `when`(nextcloudClient.createUser(nextcloudUserRequest)).thenReturn(true)

        val result = nextcloudProvisionService.provisionUser(userId)

        assertTrue(result)
        verify(nextcloudClient).createUser(nextcloudUserRequest)
    }

    @Test
    fun `should handle failure when creating Nextcloud account`() {
        val userId = "test-user-id"
        val nextcloudUserRequest = NextcloudUserRequest(userId, "test@example.com", "password")

        `when`(keycloakAdminClient.getUserEmail(userId)).thenReturn("test@example.com")
        `when`(nextcloudClient.createUser(nextcloudUserRequest)).thenReturn(false)

        val result = nextcloudProvisionService.provisionUser(userId)

        assertFalse(result)
        verify(nextcloudClient).createUser(nextcloudUserRequest)
    }
}