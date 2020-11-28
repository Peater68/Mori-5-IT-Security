package com.mori5.itsecurity.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class UserIT extends ItSecurityApplicationITBase {

    @Test
    void testGetUsersAsAdmin() {
        initWebApiClientWithToken(getAccessTokenFor(adminUser));

        client.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(adminUser.getId())
                .jsonPath("$[1].id").isEqualTo(customerUser.getId())
                .jsonPath("$[2].id").doesNotExist();
    }

    @Test
    void testGetUsersAsCustomer() {
        initWebApiClientWithToken(getAccessTokenFor(customerUser));

        client.get().uri("/users")
                .exchange()
                .expectStatus().isForbidden();
    }

}
