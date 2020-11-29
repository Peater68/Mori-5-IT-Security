package com.mori5.itsecurity.service.impl;

import com.mori5.itsecurity.cpp.CPPParserCaller;
import com.mori5.itsecurity.cpp.CreatorsImages;
import com.mori5.itsecurity.domain.*;
import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;
import com.mori5.itsecurity.errorhandling.exception.EntityNotFoundException;
import com.mori5.itsecurity.errorhandling.exception.InvalidOperationException;
import com.mori5.itsecurity.errorhandling.exception.UnprocessableEntityException;
import com.mori5.itsecurity.repository.DocumentRepository;
import com.mori5.itsecurity.repository.UserRepository;
import com.mori5.itsecurity.service.DocumentService;
import com.mori5.itsecurity.service.TagService;
import com.mori5.itsecurity.service.UserService;
import com.mori5.itsecurity.storage.StorageObject;
import com.mori5.itsecurity.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private static final String DOCUMENT_NOT_FOUND = "Document not found";
    private static final String CAFF_NOT_BOUGHT = "Caff must be bought before downloading";

    private final StorageService storageService;
    private final DocumentRepository documentRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TagService tagService;

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

        StorageObject storageObjectCaff;
        try {
            storageObjectCaff = StorageObject.builder()
                    .fileName(fileName)
                    .content(file.getBytes())
                    .contentType("caff")
                    .bucket(DocumentType.CAFF.getBucket())
                    .build();
        } catch (IOException e) {
            throw new UnprocessableEntityException("Error while reading file", ItSecurityErrors.UNPROCESSABLE_ENTITY);
        }

        CPPParserCaller cppParserCaller = new CPPParserCaller();

        CreatorsImages parsedCaff = null;
        try {
            parsedCaff = cppParserCaller.parse(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] imageInByte = getParsedPreview(parsedCaff);

        StorageObject storageObjectPreview = StorageObject.builder()
                .fileName(fileName)
                .content(imageInByte)
                .contentType("bmp")
                .bucket(DocumentType.PREVIEW.getBucket())
                .build();

        try {
            storageService.uploadObject(storageObjectCaff);
            storageService.uploadObject(storageObjectPreview);
        } catch (Exception ex) {
            storageService.deleteObject(DocumentType.CAFF.getBucket(), fileName);
            storageService.deleteObject(DocumentType.PREVIEW.getBucket(), fileName);

            throw ex;
        }

        Document document = Document.builder()
                .fileName(fileName)
                .uploader(user)
                .caffContentSize(file.getSize())
                .duration(parsedCaff.images.duration)
                .tags(getTags(parsedCaff.images.tags))
                .caption(parsedCaff.images.caption)
                .creator(parsedCaff.creatorString)
                .createdDate(LocalDateTime.of(parsedCaff.year, parsedCaff.month + 1, parsedCaff.day + 1, parsedCaff.hour + 1, parsedCaff.minute + 1).toInstant(ZoneOffset.UTC))
                .build();
        documentRepository.save(document);

        user.getUploads().add(document);
        userRepository.save(user);

        return document;
    }

    private byte[] getParsedPreview(CreatorsImages parsedCaff) {
        BufferedImage bufferedImage = new BufferedImage((int) parsedCaff.images.width, (int) parsedCaff.images.height, BufferedImage.TYPE_INT_RGB);

        int counter = 0;
        for (int i = 0; i < (int) parsedCaff.images.height; i++) {
            for (int j = 0; j < (int) parsedCaff.images.width; j++) {
                int rgb = parsedCaff.images.pixels.get(counter).red;
                rgb = (rgb << 8) + parsedCaff.images.pixels.get(counter).green;
                rgb = (rgb << 8) + parsedCaff.images.pixels.get(counter++).blue;
                bufferedImage.setRGB(j, i, rgb);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ImageIO.write(bufferedImage, "bmp", baos);
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    @Override
    @Transactional
    public StorageObject downloadCaffOrPreview(String documentId, @NotNull @Valid String type) {
        DocumentType documentType;

        try {
            documentType = DocumentType.valueOf(type);
        } catch (Exception e) {
            log.error("Error while parse document type: {}" + type);
            throw new UnprocessableEntityException("Download type error", ItSecurityErrors.UNPROCESSABLE_ENTITY);
        }

        if (documentType == DocumentType.CAFF) {
            return downloadCaff(documentId);
        } else {
            return downloadPreview(documentId);
        }
    }

    @Override
    @Transactional
    public void deleteCaff(String documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException(DOCUMENT_NOT_FOUND, ItSecurityErrors.ENTITY_NOT_FOUND));

        User user = userService.getCurrentUser();
        if (!(document.getUploader().getId().equals(user.getId()) || user.getRole().equals(Role.ADMIN))) {
            throw new AccessDeniedException("Deleting has been refused because of access violation!");
        }

        for (User u : document.getCustomers()) {
            u.getDownloads().remove(document);
        }
        for (Tag t : document.getTags()) {
            t.getContainedBy().remove(document);
        }
        documentRepository.delete(document);

        storageService.deleteObject(DocumentType.CAFF.getBucket(), document.getFileName());
        storageService.deleteObject(DocumentType.PREVIEW.getBucket(), document.getFileName());
    }

    @Override
    @Transactional
    public List<Document> getAllCaffs(String filterTag) {
        if (filterTag == null) {
            return documentRepository.findAll();
        } else {
            Tag tag = tagService.getTag(filterTag);
            return documentRepository.findByTags(tag);
        }
    }

    @Override
    @Transactional
    public Document getCaffDetailsById(String documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException(DOCUMENT_NOT_FOUND, ItSecurityErrors.ENTITY_NOT_FOUND));
    }

    @Override
    @Transactional
    public Document buyCaff(String documentId) {
        User user = userService.getCurrentUser();

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException(DOCUMENT_NOT_FOUND, ItSecurityErrors.ENTITY_NOT_FOUND));

        boolean isBought = false;
        for (Document doc : user.getDownloads()) {
            if (doc.getId().equals(documentId)) {
                isBought = true;
                break;
            }
        }

        if (isBought) {
            throw new InvalidOperationException("Caff is already bought", ItSecurityErrors.INVALID_OPERATION);
        }

        document.getCustomers().add(user);

        user.getDownloads().add(document);
        userRepository.save(user);

        return documentRepository.save(document);
    }

    @Override
    public List<Document> getBoughtCaffs() {
        return userService.getCurrentUser().getDownloads();
    }

    @Override
    public List<Document> getUpdatedCaffs() {
        return userService.getCurrentUser().getUploads();
    }

    private StorageObject downloadPreview(String documentId) {
        return downloadFile(documentId, DocumentType.PREVIEW);
    }

    private StorageObject downloadCaff(String documentId) {
        User user = userService.getCurrentUser();

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("", ItSecurityErrors.ENTITY_NOT_FOUND));

        if (!(user.getDownloads().contains(document) || document.getUploader().getId().equals(user.getId()))) {
            throw new InvalidOperationException(CAFF_NOT_BOUGHT, ItSecurityErrors.INVALID_OPERATION);
        }

        return downloadFile(documentId, DocumentType.CAFF);
    }

    private StorageObject downloadFile(String documentId, DocumentType documentType) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException(DOCUMENT_NOT_FOUND, ItSecurityErrors.ENTITY_NOT_FOUND));

        return storageService.getObject(documentType.getBucket(), document.getFileName());
    }

    private List<Tag> getTags(String csvTags) {
        String[] rawTags = csvTags.split(";");

        List<Tag> tags = new ArrayList<>();
        for (String rawTag : rawTags) {
            tags.add(tagService.saveTag(rawTag));
        }
        return tags;
    }

}
