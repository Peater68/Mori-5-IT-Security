package com.mori5.itsecurity.mapper;

import com.mori5.itsecurity.api.model.CaffDetailsDTO;
import com.mori5.itsecurity.api.model.CaffSumDTO;
import com.mori5.itsecurity.domain.Document;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentMapper {

    private DocumentMapper() {
    }


    public static CaffSumDTO mapDocumentToCaffSumDTO(Document doc) {
        if (doc == null) {
            return null;
        }

        return CaffSumDTO.builder()
                .id(doc.getId())
                .createdAt(doc.getCreatedDate().atOffset(ZoneOffset.UTC)) // TODO lehet ez is
                .uploader(doc.getUploader().getId()) // TODO ez lehet elszáll exceptionnal
                .creator(doc.getCreator())
                .build();
    }


    public static CaffDetailsDTO mapDocumentToCaffDetailsDTO(Document doc) {
        if (doc == null) {
            return null;
        }

        return CaffDetailsDTO.builder()
                .id(doc.getId())
                .uploader(doc.getUploader().getFirstName() + doc.getUploader().getLastName()) // TODO lehet itt is
                .createdAt(doc.getCreatedDate().atOffset(ZoneOffset.UTC)) // TODO lehet ez is
                .uploader(doc.getUploader().getId()) // TODO ez lehet elszáll exceptionnal
                .creator(doc.getCreator())
                .build();
    }



    public static List<CaffSumDTO> mapDocumentsListToCaffSumDTOList(List<Document> documents) {
        return documents.stream().map(DocumentMapper::mapDocumentToCaffSumDTO).collect(Collectors.toList());
    }

}
