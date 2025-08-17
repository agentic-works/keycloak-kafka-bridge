# Nextcloud API Notes

## Overview
This document provides notes and documentation related to the Nextcloud API, which is utilized for provisioning user accounts in Nextcloud when events are triggered from Keycloak.

## Nextcloud API Endpoints

### User Creation
- **Endpoint**: `POST /ocs/v1.php/cloud/users`
- **Description**: Creates a new user in Nextcloud.
- **Request Body**:
  ```json
  {
    "userid": "new_user",
    "password": "user_password",
    "email": "user@example.com"
  }
  ```
- **Response**:
  - **Success**: Returns user details and a success message.
  - **Error**: Returns an error message if the user cannot be created.

### Tenant Creation
- **Endpoint**: `POST /ocs/v1.php/cloud/tenants`
- **Description**: Creates a new tenant in Nextcloud.
- **Request Body**:
  ```json
  {
    "tenant_id": "new_tenant",
    "name": "Tenant Name",
    "admin_user": "admin_user",
    "admin_password": "admin_password"
  }
  ```
- **Response**:
  - **Success**: Returns tenant details and a success message.
  - **Error**: Returns an error message if the tenant cannot be created.

## Authentication
- Nextcloud API requires authentication via an OAuth2 token or basic authentication.
- Ensure that the appropriate credentials are provided in the request headers.

## Error Handling
- Common error responses include:
  - `400 Bad Request`: Invalid input data.
  - `401 Unauthorized`: Authentication failed.
  - `403 Forbidden`: Insufficient permissions.
  - `404 Not Found`: Endpoint does not exist.

## Rate Limiting
- Be aware of rate limits imposed by the Nextcloud API to avoid service disruptions.

## Additional Resources
- [Nextcloud API Documentation](https://docs.nextcloud.com/server/latest/developer_manual/api/index.html)
- [Keycloak Documentation](https://www.keycloak.org/documentation)

## Notes
- Ensure that the Nextcloud instance is properly configured to accept API requests from the Keycloak event listener.
- Test the integration thoroughly to handle various scenarios, including user registration and tenant creation events.