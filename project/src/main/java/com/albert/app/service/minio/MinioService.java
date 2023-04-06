package com.albert.app.service.minio;

import io.minio.*;
import io.minio.messages.Item;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.albert.app.service.ObjectStorageService;
import com.albert.app.util.UrlGenerator;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.*;

/**
 * This class implements the ObjectStorageService interface and provides
 * methods to interact with a MinIO object storage service.
 */
@Service("minioService")
public class MinioService implements ObjectStorageService {

    // minIO configurations
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucket;

    @Value("${ip.address}")
    private String ipAddress;

    @Value("${spring.boot.port}")
    private String port;

    private MinioClient minioClient;

    /**
     * Initializes MinioClient and makes MinIO bucket if doesn't exist.
     */
    @PostConstruct
    private void init() throws Exception {
        minioClient = new MinioClient.Builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }

    @Override
    public String storeObject(InputStream inputStream, String object_name, String contentType) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(object_name)
                    .contentType(contentType)
                    .stream(inputStream, -1, 10485760)
                    .build());
            UrlGenerator metadata = new UrlGenerator(ipAddress, port, object_name);
            return metadata.url;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload object to Minio", e);
        }
    }

    @Override
    public InputStream getObject(String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(objectName).build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to download object from Minio", e);
        }
    }

    @Override
    public void deleteObject(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete object from Minio", e);
        }
    }

    @Override
    public List<UrlGenerator> getListObject() {
        try {
            List<UrlGenerator> objects = new ArrayList<>();
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucket).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                UrlGenerator urlData = new UrlGenerator(
                        ipAddress,
                        port,
                        item.objectName());
                objects.add(urlData);
            }
            return objects;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get object list from Minio", e);
        }
    }

}