package com.mori5.itsecurity.integration;

import com.mori5.itsecurity.api.model.LoginRequestDTO;
import com.mori5.itsecurity.api.model.RefreshTokenDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

public class AuthIntegrationTests extends ItSecurityApplicationIntegrationTests {

    @Test
    void testLogin() {
        initWebApiClient();

        LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder()
                .username("admin")
                .password("admin")
                .build();

        client.post().uri("/auth/login")
                .body(Mono.just(loginRequestDTO), LoginRequestDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.tokens.accessToken").exists()
                .jsonPath("$.tokens.refreshToken").exists();
    }

    @Test
    void testRenew() {
        initWebApiClient();

        RefreshTokenDTO refreshTokenDTO = RefreshTokenDTO.builder()
                .refreshToken(getRefreshTokenFor(adminUser))
                .build();

        client.post().uri("/auth/token")
                .body(Mono.just(refreshTokenDTO), RefreshTokenDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.accessToken").exists()
                .jsonPath("$.refreshToken").exists();
    }

}
