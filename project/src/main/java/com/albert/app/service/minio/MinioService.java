package com.albert.app.service.minio;

import io.minio.*;
import io.minio.messages.Item;

import org.springframework.stereotype.Service;

import com.albert.app.model.Object;
import com.albert.app.service.ObjectStorageService;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.*;

/**
 * This class implements the ObjectStorageService interface and provides
 * methods to interact with a MinIO object storage service.
 */
@Service
public class MinioService implements ObjectStorageService {

    private String endpoint = System.getenv("MINIO_ENDPOINT");

    private String accessKey = System.getenv("MINIO_ACCESS_KEY");

    private String secretKey = System.getenv("MINIO_SECRET_KEY");

    private String bucket = System.getenv("MINIO_BUCKET_NAME");

    MinioClient minioClient = new MinioClient.Builder()
            .endpoint(endpoint)
            .credentials(accessKey, secretKey)
            .build();

    /**
     * Initializes MinIO bucket if doesn't exist.
     */
    @PostConstruct
    public void init() throws Exception {

        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }

    @Override
    public String upload(InputStream inputStream, String file_name, String contentType) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(file_name)
                    .contentType(contentType)
                    .stream(inputStream, -1,
                            10485760)
                    .build());
            Object file = new Object();
            file.name = file_name;
            file.setUrl();
            return file.url;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to Minio", e);
        }
    }

    @Override
    public InputStream download(String fileName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(fileName).build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file from Minio", e);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(fileName).build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file from Minio", e);
        }
    }

    @Override
    public List<Object> list() {
        try {
            List<Object> files = new ArrayList<>();
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucket).build());
            for (Result<Item> result : results) {
                Object file = new Object();
                Item item = result.get();
                file.name = item.objectName();
                file.setUrl();
                files.add(file);
            }
            return files;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get file list from Minio", e);
        }
    }

}