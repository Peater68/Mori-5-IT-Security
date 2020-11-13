package com.mori5.itsecurity.storage;

public interface StorageService {
    void uploadObject(StorageObject object);

    StorageObject getObject(String bucket, String fullPath);

    void deleteObject(String bucket, String fullPath);

    void createBucketIfNotExists(String bucketName);
}
