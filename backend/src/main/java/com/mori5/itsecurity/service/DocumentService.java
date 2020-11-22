package com.mori5.itsecurity.service;

import com.mori5.itsecurity.domain.Document;
import com.mori5.itsecurity.storage.StorageObject;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface DocumentService {

    Document uploadCaff(MultipartFile file);

    StorageObject downloadCaffOrPreview(String documentId, @NotNull @Valid String type);

    void deleteCaff(String documentId);

    List<Document> getAllCaffs(String filterKey);

    Document getCaffDetailsById(String documentId);

    Document buyCaff(String documentId);

    List<Document> getBoughtCaffs();

    List<Document> getUpdatedCaffs();

}
