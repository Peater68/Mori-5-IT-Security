package com.mori5.itsecurity.controller;

import com.mori5.itsecurity.api.CaffApi;
import com.mori5.itsecurity.api.model.CaffDetailsDTO;
import com.mori5.itsecurity.api.model.CaffSumDTO;
import com.mori5.itsecurity.domain.DocumentType;
import com.mori5.itsecurity.mapper.DocumentMapper;
import com.mori5.itsecurity.security.AuthoritiesConstants;
import com.mori5.itsecurity.service.DocumentService;
import com.mori5.itsecurity.storage.StorageObject;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DocumentController implements CaffApi {

    private static final String JPEG = "jpeg";
    private static final String ATTACHMENT_FILENAME = "attachment; filename=";

    private final DocumentService documentService;

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CUSTOMER})
    public ResponseEntity<CaffDetailsDTO> uploadCaff(@Valid MultipartFile file) {
        return ResponseEntity.ok(DocumentMapper.mapDocumentToCaffDetailsDTO(documentService.uploadCaff(file)));
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CUSTOMER})
    public ResponseEntity<Void> deleteCaffById(String caffId) {
        documentService.deleteCaff(caffId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CUSTOMER})
    public ResponseEntity<Resource> downloadPreviewOrCaffFile(String caffId, @NotNull @Valid String type) {
        StorageObject storageObject = documentService.downloadCaffOrPreview(caffId, type);

        ByteArrayResource resource = new ByteArrayResource(storageObject.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT_FILENAME + storageObject.getFileName());

        if (type.equals(DocumentType.PREVIEW.getName())) {
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(storageObject.getContent().length)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(storageObject.getContent().length)
                    .body(resource);
        }

    }

    @GetMapping(value = "/api/caffss/{caffId}/asd", produces = { "*/*"})
    public ResponseEntity<Resource> downloadPreviewOrCaffFile2(@PathVariable String caffId, @NotNull @Valid String type) {
        StorageObject storageObject = documentService.downloadCaffOrPreview(caffId, type);

        ByteArrayResource resource = new ByteArrayResource(storageObject.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT_FILENAME + storageObject.getFileName() +".caff");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        if (type.equals(DocumentType.PREVIEW.getName())) {
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(storageObject.getContent().length)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(storageObject.getContent().length)
                    .body(resource);

        }

    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CUSTOMER})
    public ResponseEntity<List<CaffSumDTO>> getAllCaffs(String filterTag) {
        return ResponseEntity.ok(DocumentMapper.mapDocumentsListToCaffSumDTOList(documentService.getAllCaffs(filterTag)));
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CUSTOMER})
    public ResponseEntity<CaffDetailsDTO> getCaffDetailsById(String caffId) {
        return ResponseEntity.ok(DocumentMapper.mapDocumentToCaffDetailsDTO(documentService.getCaffDetailsById(caffId)));
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CUSTOMER})
    public ResponseEntity<CaffDetailsDTO> buyCaff(String caffId) {
        return ResponseEntity.ok(DocumentMapper.mapDocumentToCaffDetailsDTO(documentService.buyCaff(caffId)));
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CUSTOMER})
    public ResponseEntity<List<CaffSumDTO>> getBoughtCaffs() {
        return ResponseEntity.ok(DocumentMapper.mapDocumentsListToCaffSumDTOList(documentService.getBoughtCaffs()));
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CUSTOMER})
    public ResponseEntity<List<CaffSumDTO>> getUploadedCaffs() {
        return ResponseEntity.ok(DocumentMapper.mapDocumentsListToCaffSumDTOList(documentService.getUpdatedCaffs()));
    }

}
