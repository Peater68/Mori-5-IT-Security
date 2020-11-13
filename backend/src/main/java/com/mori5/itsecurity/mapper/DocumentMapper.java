package com.mori5.itsecurity.mapper;

import com.mori5.itsecurity.domain.Document;

public class DocumentMapper {

    private DocumentMapper() {
    }

    // TODO ha van API
/*    public static DocumentDetailsDTO fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }

        return DocumentDetailsDTO.builder()
                .id(doc.getId())
                .fileName(doc.getFileName())
                .uploadDate(doc.getModifiedAt())
                .type(doc.getType())
                .build();
    }*/
}
