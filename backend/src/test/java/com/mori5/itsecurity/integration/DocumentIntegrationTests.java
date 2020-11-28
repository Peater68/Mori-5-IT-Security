package com.mori5.itsecurity.integration;

import com.mori5.itsecurity.domain.Document;
import com.mori5.itsecurity.domain.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class DocumentIntegrationTests extends ItSecurityApplicationIntegrationTests {

    private static final Tag tag = Tag.builder()
            .id("t497255a-9f4c-42a0-83db-abc3ce6c34a4")
            .title("TagTitle")
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
    private static final Document document1 = Document.builder()
            .id("d497255a-9f4c-42a0-83db-abc3ce6c34a4")
            .uploader(customerUser)
            .caption("Caption")
            .creator("TestCreate")
            .customers(List.of(adminUser))
            .duration(1000L)
            .fileName("file")
            .caffContentSize(123456L)
            .tags(List.of(tag))
            .createdDate(Instant.now())
            .modifiedAt(Instant.now())
            .build();
    private static final Document document2 = Document.builder()
            .id("d597255a-9f4c-42a0-83db-abc3ce6c34a4")
            .uploader(customerUser)
            .caption("Caption2")
            .creator("TestCreate2")
            .duration(1000L)
            .fileName("file2")
            .caffContentSize(123456L)
            .tags(List.of())
            .createdDate(Instant.now())
            .modifiedAt(Instant.now())
            .build();

    @BeforeEach
    void setup() {
        when(documentRepository.findAll()).thenReturn(
                List.of(document1, document2)
        );
        when(documentRepository.findById(document1.getId())).thenReturn(
                Optional.of(document1)
        );
    }

    @Test
    void testGetDocuments() {
        initWebApiClientWithToken(getAccessTokenFor(adminUser));

        client.get().uri("/caffs")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(document1.getId())
                .jsonPath("$[0].creator").isEqualTo(document1.getCreator())
                .jsonPath("$[0].createdAt").exists()
                .jsonPath("$[1].id").isEqualTo(document2.getId())
                .jsonPath("$[1].creator").isEqualTo(document2.getCreator())
                .jsonPath("$[1].createdAt").exists();
    }

    @Test
    void testGetDocument() {
        initWebApiClientWithToken(getAccessTokenFor(adminUser));

        client.get().uri("/caffs/" + document1.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(document1.getId())
                .jsonPath("$.creator").isEqualTo(document1.getCreator())
                .jsonPath("$.createdAt").exists()
                .jsonPath("$.duration").isEqualTo(document1.getDuration())
                .jsonPath("$.tags").exists()
                .jsonPath("$.caption").isEqualTo(document1.getCaption());
    }

}
