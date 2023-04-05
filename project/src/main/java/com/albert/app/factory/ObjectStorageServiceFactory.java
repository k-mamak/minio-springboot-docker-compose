package com.albert.app.factory;

import com.albert.app.service.ObjectStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The ObjectStorageServiceFactory class creates ObjectStorageService based on
 * the value of the OBJECT_STORAGE_SERVICE environment variable.
 */
@Service
public class ObjectStorageServiceFactory {

    // Autowiring two different ObjectStorageService implementations
    @Autowired
    @Qualifier("minioService")
    private ObjectStorageService minioService;

    @Autowired
    @Qualifier("s3BucketService")
    private ObjectStorageService s3BucketService;

    /**
     * This method returns an instance of ObjectStorageService based on the value of
     * the OBJECT_STORAGE_SERVICE environment variable.
     * 
     * @return ObjectStorageService instance
     * @throws IllegalArgumentException if OBJECT_STORAGE_SERVICE
     *                                  environment variable is not set or has an
     *                                  invalid value.
     */
    public ObjectStorageService getObjectStorageService() {

        // Get the value of OBJECT_STORAGE_SERVICE environment variable
        String objectStorageServiceName = System.getenv("OBJECT_STORAGE_SERVICE");

        // Throw an exception if the environment variable is not set or is empty
        if (objectStorageServiceName == null || objectStorageServiceName.isEmpty()) {
            throw new IllegalArgumentException("OBJECT_STORAGE_SERVICE environment variable is not set");
        }

        // Return the appropriate ObjectStorageService instance based on the environment
        // variable value
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
