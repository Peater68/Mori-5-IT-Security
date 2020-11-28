package com.mori5.itsecurity.integration;

import com.mori5.itsecurity.domain.Role;
import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.repository.DocumentRepository;
import com.mori5.itsecurity.repository.TagRepository;
import com.mori5.itsecurity.repository.UserRepository;
import com.mori5.itsecurity.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItSecurityApplicationIntegrationTests {

    @LocalServerPort
    protected int randomServerPort;

    @Autowired
    private TokenService tokenService;

    @MockBean
    protected TagRepository tagRepository;
    @MockBean
    protected UserRepository userRepository;
    @MockBean
    protected DocumentRepository documentRepository;

    protected static User adminUser = User.builder()
            .id("e497255a-9f4c-42a0-83db-abc3ce6c34a4")
            .username("admin")
            .firstName("Admin")
            .lastName("Test")
            .password("notHashedPwd")
            .role(Role.ADMIN)
            .email("admin@test.hu")
            .uploads(List.of())
            .downloads(List.of())
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .isBanned(false)
            .build();
    protected static User customerUser = User.builder()
            .id("c497255a-9f4c-42a0-83db-abc3ce6c34a4")
            .username("customer")
            .firstName("Customer")
            .lastName("Test")
            .password("notHashedPwd")
            .role(Role.CUSTOMER)
            .email("customer@test.hu")
            .uploads(List.of())
            .downloads(List.of())
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .isBanned(false)
            .build();

    protected WebTestClient client;

    @BeforeEach
    private void setupDefaults() {
        when(userRepository.findByUsername("admin")).thenReturn(
                Optional.of(
                        adminUser
                )
        );
        when(userRepository.findByUsername("customer")).thenReturn(
                Optional.of(
                        adminUser
                )
        );
        when(userRepository.findAll()).thenReturn(
                List.of(adminUser, customerUser)
        );
    }

    protected String getAccessTokenFor(User user) {
        return tokenService.generateAccessToken(user.getUsername(), user.getId(), user.getRole());
    }

    protected void initWebApiClientWithToken(String token) {
        client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + randomServerPort + "/api")
                .responseTimeout(Duration.ofMinutes(1))
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessTokenFor(adminUser))
                .build();
    }

}
