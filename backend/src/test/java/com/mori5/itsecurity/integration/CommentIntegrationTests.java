package com.mori5.itsecurity.integration;

import com.mori5.itsecurity.domain.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;

public class CommentIntegrationTests extends ItSecurityApplicationIntegrationTests {

    public static final Comment comment = Comment.builder()
            .id("c497255a-9f4c-42a0-83db-abc3ce6c34a4")
            .document(DocumentIntegrationTests.document1)
            .user(adminUser)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();

    @BeforeEach
    void setup() {
        when(commentRepository.findByDocumentId(DocumentIntegrationTests.document1.getId())).thenReturn(
                List.of(comment)
        );
    }

    @Test
    void testGetComments() {
        initWebApiClientWithToken(getAccessTokenFor(adminUser));

        client.get().uri("/caffs/" + DocumentIntegrationTests.document1.getId() + "/comments")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(comment.getId())
                .jsonPath("$[0].comment").isEqualTo(comment.getCommentMessage())
                .jsonPath("$[0].user").exists()
                .jsonPath("$[0].createdAt").exists();
    }

}
