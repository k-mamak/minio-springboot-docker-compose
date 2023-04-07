package com.albert.app.service.minio;

import io.minio.MinioClient;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.messages.Item;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.albert.app.service.ObjectStorageService;
import com.albert.app.util.UrlGenerator;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the ObjectStorageService interface and provides
 * methods to interact with a MinIO object storage service.
 */
@Service("minioService")
public class MinioService implements ObjectStorageService {

    /**
     * The endpoint of the MinIO server.
     */
    @Value("${minio.endpoint}")
    private String endpoint;

    /**
     * The access key for the MinIO server.
     */
    @Value("${minio.accessKey}")
    private String accessKey;

    /**
     * The secret key for the MinIO server.
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    /**
     * The name of the bucket to store objects in.
     */
    @Value("${minio.bucketName}")
    private String bucket;

    /**
     * The IP address of the server.
     */
    @Value("${ip.address}")
    private String ipAddress;

    /**
     * The port number of the server.
     */
    @Value("${spring.boot.port}")
    private String port;

    /**
     * MinioClient to connect MinIO.
     */
    private MinioClient minioClient;

    /**
     * Maximum part size for uploading file in byte.
     */
    // Avarage file size can be assumed as 10 MB.
    private static final long MAX_PART_SIZE = 10 * 1024 * 1024;

    /**
     * Initializes MinioClient and makes MinIO bucket if doesn't exist.
     */
    @PostConstruct
    private void init() throws Exception {
        minioClient = new MinioClient.Builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucket)
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucket)
                    .build());
        }
    }

    @Override
    public final String storeObject(final InputStream inputStream,
            final String objectName,
            final String contentType) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .contentType(contentType)
                    .stream(inputStream, -1,
                            MAX_PART_SIZE)
                    .build());
            UrlGenerator metadata = new UrlGenerator(
                    ipAddress, port, objectName);
            return metadata.getUrl();
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload object to Minio", e);
        }
    }

    @Override
    public final InputStream getObject(final String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to download object from Minio",
                    e);
        }
    }

    @Override
    public final void deleteObject(final String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete object from Minio", e);
        }
    }

    @Override
    public final List<UrlGenerator> getListObject() {
        try {
            List<UrlGenerator> objects = new ArrayList<>();
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucket)
                            .build());
            for (Result<Item> result : results) {
                Item item = result.get();
                UrlGenerator urlData = new UrlGenerator(
                        ipAddress, port, item.objectName());
                objects.add(urlData);
            }
            return objects;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get object list from Minio",
                    e);
        }
    }
}
