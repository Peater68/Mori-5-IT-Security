package com.mori5.itsecurity.integration;

import com.mori5.itsecurity.domain.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.when;

public class TagIntegrationTests extends ItSecurityApplicationIntegrationTests {

    public static final Tag tag = Tag.builder()
            .id("f497255a-9f4c-42a0-83db-abc3ce6c34a4")
            .title("TEST_TITLE")
            .build();

    @BeforeEach
    void setup() {
        when(tagRepository.findAll()).thenReturn(
                new ArrayList<>(
                        Collections.singletonList(tag)
                )
        );
    }

    @Test
    void testGetTagsAsAdmin() {
        initWebApiClientWithToken(getAccessTokenFor(adminUser));

        client.get().uri("/tags")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(tag.getId())
                .jsonPath("$[0].title").isEqualTo(tag.getTitle());
    }

    @Test
    void testGetTagsAsCustomer() {
        initWebApiClientWithToken(getAccessTokenFor(customerUser));

        client.get().uri("/tags")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(tag.getId())
                .jsonPath("$[0].title").isEqualTo(tag.getTitle());
    }
}
