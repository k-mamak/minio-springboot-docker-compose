package com.albert.app.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.albert.app.model.StoredObject;

@Service
public interface ObjectStorageService {
    /**
     * Uploads a file to Object Storage and returns the URL of the uploaded file.
     *
     * @param inputStream the InputStream of the file to upload
     * @param fileName    the name of the file to upload
     * @param contentType the content type of the file to upload
     * @return the URL of the uploaded file
     */
    String store_object(InputStream inputStream, String fileName, String contentType);

    /**
     * Downloads a file from Object Storage.
     *
     * @param fileName the name of the file to download
     * @return the InputStream of the downloaded file
     */
    InputStream get_object(String fileName);

    /**
     * Deletes a file from Object Storage.
     *
     * @param fileName the name of the file to delete
     */
    void delete_object(String fileName);

    /**
     * Lists all files stored in Object Storage.
     *
     * @return a List of all files stored in Object Storage
     */
    List<StoredObject> objects_list();
}
