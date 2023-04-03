package com.albert.app.factory;

import com.albert.app.service.ObjectStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ObjectStorageServiceFactory {

    @Autowired
    @Qualifier("minioService")
    private ObjectStorageService minioService;

    @Autowired
    @Qualifier("s3BucketService")
    private ObjectStorageService s3BucketService;

    public ObjectStorageService getObjectStorageService() {
        String objectStorageServiceName = System.getenv("OBJECT_STORAGE_SERVICE");

        if (objectStorageServiceName == null || objectStorageServiceName.isEmpty()) {
            throw new IllegalArgumentException("OBJECT_STORAGE_SERVICE environment variable is not set");
        }

        if (objectStorageServiceName.equalsIgnoreCase("minioService")) {
            return minioService;
        } else if (objectStorageServiceName.equalsIgnoreCase("s3BucketService")) {
            return s3BucketService;
        } else {
            throw new IllegalArgumentException(
                    "Invalid value for OBJECT_STORAGE_SERVICE environment variable: " + objectStorageServiceName);
        }
    }
}
