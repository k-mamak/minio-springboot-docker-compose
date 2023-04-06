package com.albert.app.factory;

import com.albert.app.service.ObjectStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The ObjectStorageServiceFactory class creates ObjectStorageService based on
 * the value of the objectStorageServiceName property.
 */
@Service
public class ObjectStorageServiceFactory {

    @Value("${objectStorageServiceName}")
    private String objectStorageServiceName;

    @Autowired
    @Qualifier("minioService")
    private ObjectStorageService minioService;

    /**
     * This method returns an instance of ObjectStorageService based on the value of
     * the objectStorageServiceName property.
     * 
     * @return ObjectStorageService instance
     * @throws IllegalArgumentException if objectStorageServiceName
     *                                  property is not set or has an
     *                                  invalid value.
     */
    public ObjectStorageService getObjectStorageService() {

        // Throw an exception if the environment variable is not set or is empty
        if (objectStorageServiceName == null || objectStorageServiceName.isEmpty()) {
            throw new IllegalArgumentException("objectStorageServiceName property is not set");
        }

        // Return the appropriate ObjectStorageService instance based on the property
        if (objectStorageServiceName.equalsIgnoreCase("minioService")) {
            return minioService;
        } else {
            throw new IllegalArgumentException(
                    "Invalid value for objectStorageServiceName property: " + objectStorageServiceName);
        }
    }
}
