package com.mori5.itsecurity.service;

import com.mori5.itsecurity.domain.Document;
import com.mori5.itsecurity.storage.StorageObject;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    Document uploadCaff(MultipartFile file);

    StorageObject downloadPreview(String documentId);

    StorageObject downloadCaff(String documentId);

    void deleteCaff(String documentId);
}
