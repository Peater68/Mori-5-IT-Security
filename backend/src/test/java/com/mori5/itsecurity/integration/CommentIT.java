package com.mori5.itsecurity.integration;

import com.mori5.itsecurity.domain.Comment;
import com.mori5.itsecurity.domain.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;

public class CommentIT extends ItSecurityApplicationITBase {

    public static final Document document = DocumentIT.document1;
    public static final Comment comment = Comment.builder()
            .id("c497255a-9f4c-42a0-83db-abc3ce6c34a4")
            .document(document)
            .user(adminUser)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();

    @BeforeEach
    void setup() {
        when(commentRepository.findByDocumentId(document.getId())).thenReturn(
                List.of(comment)
        );
    }

    @Test
    void testGetComments() {
        initWebApiClientWithToken(getAccessTokenFor(adminUser));

        client.get().uri("/caffs/" + document.getId() + "/comments")
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
