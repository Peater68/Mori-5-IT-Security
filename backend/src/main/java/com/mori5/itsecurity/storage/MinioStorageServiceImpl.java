package com.mori5.itsecurity.storage;


import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class MinioStorageServiceImpl implements StorageService {
    private final String endpoint;
    private final String accessKey;
    private final String secretKey;

    private MinioClient minioClient;

    public MinioStorageServiceImpl(@Value("${itsecurity.minio.endpoint}") String endpoint,
                                   @Value("${itsecurity.minio.accesskey}") String accessKey,
                                   @Value("${itsecurity.minio.secretkey}") String secretKey) {
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    // TODO esetleg itt a hibakezelést lehetne csinosítani, ne csak log üzenetek legyenek, hanem szálljon el az egész, ha valami hiba van

    @PostConstruct
    private void init() {
        try {
            minioClient = new MinioClient(endpoint, accessKey, secretKey);
        } catch (Exception e) {
            log.error("Could not initialize Minio client!", e);
        }
    }

    @Override
    public void uploadObject(StorageObject object) {
        try (InputStream is = new ByteArrayInputStream(object.getContent())) {
            minioClient.putObject(object.getBucket(), object.getFileName(), is, (long) is.available(), null, null, object.getContentType());
        } catch (Exception e) {
            log.error("Could not upload file!", e);
        }
    }

    @Override
    public StorageObject getObject(String bucket, String fileName) {
        try (InputStream stream = minioClient.getObject(bucket, fileName)) {
            return StorageObject.builder()
                    .bucket(bucket)
                    .fileName(fileName)
                    .content(toByteArray(stream))
                    .build();
        } catch (Exception e) {
            log.error("Could not get object (bucket: {}, fileName: {}) from storage!", bucket, fileName, e);
            return StorageObject.builder().build();
        }
    }

    @Override
    public void deleteObject(String bucket, String fileName) {
        try {
            minioClient.removeObject(bucket, fileName);
        } catch (Exception e) {
            log.error("Failed to delete object(bucket: {}, fileName: {}) from storage!", bucket, fileName, e);
        }
    }

    @Override
    public void createBucketIfNotExists(String bucketName) {
        try {
            boolean isExist = minioClient.bucketExists(bucketName);
            if (isExist) {
                log.debug("Bucket" + bucketName + " already exists!");
            } else {
                minioClient.makeBucket(bucketName);
            }
        } catch (Exception e) {
            log.error("Could nor create bucket " + bucketName, e);
        }
    }

    private byte[] toByteArray(InputStream is) {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            return buffer.toByteArray();
        } catch (IOException e) {
            log.error("Could not convert Minio input stream to byte array!", e);
            return new byte[0];
        }
    }
}
