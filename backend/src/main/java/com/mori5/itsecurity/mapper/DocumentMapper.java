package com.mori5.itsecurity.mapper;

import com.mori5.itsecurity.api.model.CaffDetailsDTO;
import com.mori5.itsecurity.api.model.CaffSumDTO;
import com.mori5.itsecurity.domain.Document;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentMapper {

    public static CaffSumDTO mapDocumentToCaffSumDTO(Document doc) {
        if (doc == null) {
            return null;
        }

        return CaffSumDTO.builder()
                .id(doc.getId())
                .createdAt(doc.getCreatedDate().atOffset(ZoneOffset.UTC)) // TODO lehet ez is
                .creator(doc.getCreator())
                .build();
    }

    public static CaffDetailsDTO mapDocumentToCaffDetailsDTO(Document doc) {
        if (doc == null) {
            return null;
        }

        return CaffDetailsDTO.builder()
                .id(doc.getId())
                .createdAt(doc.getCreatedDate().atOffset(ZoneOffset.UTC)) // TODO lehet ez is
                .creator(doc.getCreator())
                .build();
    }

    public static List<CaffSumDTO> mapDocumentsListToCaffSumDTOList(List<Document> documents) {
        return documents.stream().map(DocumentMapper::mapDocumentToCaffSumDTO).collect(Collectors.toList());
    }

}
