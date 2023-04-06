package com.albert.app.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.albert.app.util.UrlGenerator;

@Service
public interface ObjectStorageService {
    /**
     * Uploads an object to Object Storage and returns the URL of the uploaded
     * object.
     *
     * @param inputStream the InputStream of the object to upload
     * @param objectName  the name of the object to upload
     * @param contentType the content type of the object to upload
     * @return the URL of the uploaded object
     */
    String storeObject(InputStream inputStream, String objectName, String contentType);

    /**
     * Downloads an object from Object Storage.
     *
     * @param objectName the name of the object to download
     * @return the InputStream of the downloaded object
     */
    InputStream getObject(String objectName);

    /**
     * Deletes an object from Object Storage.
     *
     * @param objectName the name of the object to delete
     */
    void deleteObject(String objectName);

    /**
     * Lists name and url of all objects stored in Object Storage.
     *
     * @return a List of all objects stored in Object Storage
     */
    List<UrlGenerator> getListObject();
}
