package com.albert.app.service.minio;

import io.minio.*;
import io.minio.messages.Item;

import org.springframework.stereotype.Service;

import com.albert.app.model.StoredObject;
import com.albert.app.service.ObjectStorageService;

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
    private void init() throws Exception {

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
                    .stream(inputStream, -1,
                            10485760)
                    .build());
            StoredObject object = new StoredObject(object_name);
            return object.getUrl();
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
    public List<StoredObject> getListObject() {
        try {
            List<StoredObject> objects = new ArrayList<>();
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucket).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                StoredObject object = new StoredObject(item.objectName());
                objects.add(object);
            }
            return objects;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get object list from Minio", e);
        }
    }

}