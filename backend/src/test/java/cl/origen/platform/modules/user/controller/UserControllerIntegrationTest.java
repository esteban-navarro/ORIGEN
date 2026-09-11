package cl.origen.platform.modules.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class UserControllerIntegrationTest {

    @Container
    @ServiceConnection
    static MSSQLServerContainer<?> sqlServer =
            new MSSQLServerContainer<>("mcr.microsoft.com/mssql/server:2022-latest");

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "USER_CREATE")
    void shouldCreateUserSuccessfully() throws Exception {

        String request = """
                {
                    "username": "integration.user",
                    "email": "integration@origen.cl",
                    "password": "Password123",
                    "firstName": "Integration",
                    "lastName": "User"
                }
                """;

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.data.username").value("integration.user"))
                .andExpect(jsonPath("$.data.email").value("integration@origen.cl"))
                .andExpect(jsonPath("$.data.firstName").value("Integration"))
                .andExpect(jsonPath("$.data.lastName").value("User"))
                .andExpect(jsonPath("$.data.enabled").value(true));
    }

    @Test
    @WithMockUser(authorities = "USER_CREATE")
    void shouldReturnConflictWhenUsernameAlreadyExists() throws Exception {

        String firstRequest = """
                {
                    "username": "duplicate.user",
                    "email": "first@origen.cl",
                    "password": "Password123",
                    "firstName": "First",
                    "lastName": "User"
                }
                """;

        String secondRequest = """
                {
                    "username": "duplicate.user",
                    "email": "second@origen.cl",
                    "password": "Password123",
                    "firstName": "Second",
                    "lastName": "User"
                }
                """;

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(firstRequest))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(secondRequest))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value("ERROR"));
    }

    @Test
    @WithMockUser(authorities = {"USER_CREATE", "USER_READ"})
    void shouldGetUsersSuccessfully() throws Exception {

        String request = """
                {
                    "username": "get.user",
                    "email": "get@origen.cl",
                    "password": "Password123",
                    "firstName": "Get",
                    "lastName": "User"
                }
                """;

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[?(@.username == 'get.user')]").exists());
    }

    @Test
    @WithMockUser(authorities = "USER_READ")
    void shouldReturnForbiddenWhenUserWithoutCreatePermissionAttemptsToCreateUser() throws Exception {

        String request = """
                {
                    "username": "forbidden.user",
                    "email": "forbidden@origen.cl",
                    "password": "Password123",
                    "firstName": "Forbidden",
                    "lastName": "User"
                }
                """;

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isForbidden());
    }

}
