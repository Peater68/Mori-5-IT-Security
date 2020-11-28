package com.mori5.itsecurity.integration;

import com.mori5.itsecurity.domain.Tag;
import com.mori5.itsecurity.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TagIntegrationTests extends ItSecurityApplicationIntegrationTests {

    private static final Tag tag = Tag.builder()
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

        initWebApiClientWithToken(getAccessTokenFor(adminUser));
    }

    @Test
    void testGetTags() {
        client.get().uri("/tags")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(tag.getId())
                .jsonPath("$[0].title").isEqualTo(tag.getTitle());
    }

}