package com.mori5.itsecurity.service.impl;

import com.mori5.itsecurity.domain.Document;
import com.mori5.itsecurity.domain.DocumentType;
import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;
import com.mori5.itsecurity.errorhandling.exception.EntityNotFoundException;
import com.mori5.itsecurity.errorhandling.exception.InvalidOperationException;
import com.mori5.itsecurity.errorhandling.exception.NoUserInContextException;
import com.mori5.itsecurity.repository.DocumentRepository;
import com.mori5.itsecurity.repository.UserRepository;
import com.mori5.itsecurity.service.DocumentService;
import com.mori5.itsecurity.service.UserService;
import com.mori5.itsecurity.storage.StorageObject;
import com.mori5.itsecurity.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String DOCUMENT_NOT_FOUND = "Document not found";
    private static final String NOT_LOGGED_IN = "There is not logged in user";

    private final StorageService storageService;
    private final DocumentRepository documentRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostConstruct
    private void init() {
        for (DocumentType documentType : DocumentType.values()) {
            storageService.createBucketIfNotExists(documentType.getBucket());
        }
    }

    @Override
    @Transactional
    public Document uploadCaff(MultipartFile file) {
        User user = userService.getCurrentUser();

        long currentTimeInMillis = new Date().getTime();
        String fileName = currentTimeInMillis + "_" + file.getName();

        // TODO csekkolni a file nevet, nehogy valami izé legyen

        // TODO ezt egyből lerakni
        StorageObject storageObjectCaff = null;
        try {
            storageObjectCaff = StorageObject.builder()
                    .fileName(fileName)
                    .content(file.getBytes())
                    .contentType(file.getContentType())
                    .bucket(DocumentType.CAFF.getBucket())
                    .build();
        } catch (IOException e) { // Ezt majd szebben kezelni
            e.printStackTrace();
        }

        // TODO itt kell majd áthívni a parserhez és a contentet beállítani, amit viszakapunk
        StorageObject storageObjectPreview = StorageObject.builder()
                .fileName(fileName)
                //.content()
                //.contentType() valami kép
                .bucket(DocumentType.PREVIEW.getBucket())
                .build();


        // TODO hibakezelést megvalósítani, hogy vagy egybe sikerül, vagy sehogy se. Tranzakció?
        storageService.uploadObject(storageObjectCaff);
        storageService.uploadObject(storageObjectPreview);


        // TODO cascadet megnezni, ha torlunk usert, toroljuk a feltltott kepeit is?
        Document document = Document.builder()
                .fileName(fileName)
                .uploader(user)
                .customers(null)
                .build();

        user.getUploads().add(document);

        userRepository.save(user);
        // TODO itt még lehet nem kap ID-t a document, pedig az kell

        return document;
    }

    @Override
    public StorageObject downloadPreview(String documentId) {
        return downloadFile(documentId, DocumentType.PREVIEW);
    }

    @Override
    public StorageObject downloadCaff(String documentId) {
        // TODO csekkolni, hogy megvette-e már. Ha igen, esetleg hibaüzenet, hogy többet ne tudja
        // TODO csekkolni, hogy aki feltöltötte, az is letudja tölteni?
        // TODO így a szervezés lehet nem jó, mert aki letölti, ott be kell állítani a fieldeket, hogy ki mit töltött le
        return downloadFile(documentId, DocumentType.CAFF);
    }

    @Override
    public void deleteCaff(String documentId) {
        // TODO kidobni a previewt és a caffot is, de előtte mindenféle ellenőrzést, hogy tuti a megfelelő user törli-e (vagy admin)
        User user = userService.getCurrentUser();
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new EntityNotFoundException(DOCUMENT_NOT_FOUND, ItSecurityErrors.ENTITY_NOT_FOUND));

        // TODO hibakezelés
        storageService.deleteObject(DocumentType.CAFF.getBucket(), document.getFileName());
        storageService.deleteObject(DocumentType.PREVIEW.getBucket(), document.getFileName());
    }

    private StorageObject downloadFile(String documentId, DocumentType documentType) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new EntityNotFoundException(DOCUMENT_NOT_FOUND, ItSecurityErrors.ENTITY_NOT_FOUND));
        return storageService.getObject(documentType.getBucket(), document.getFileName());
    }
}
