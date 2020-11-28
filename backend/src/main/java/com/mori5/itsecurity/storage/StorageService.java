package com.mori5.itsecurity.storage;

import java.io.IOException;

public interface StorageService {
    void uploadObject(StorageObject object) throws IOException;

    StorageObject getObject(String bucket, String fullPath);

    void deleteObject(String bucket, String fullPath);

    void createBucketIfNotExists(String bucketName);
}
