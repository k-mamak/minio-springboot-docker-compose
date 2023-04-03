package com.albert.app.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.albert.app.model.Object;

@Service
public interface ObjectStorageService {
    /**
     * Uploads a file to MinIO and returns the URL of the uploaded file.
     *
     * @param inputStream the InputStream of the file to upload
     * @param fileName    the name of the file to upload
     * @param contentType the content type of the file to upload
     * @return the URL of the uploaded file
     */
    String upload(InputStream inputStream, String fileName, String contentType);

    /**
     * Downloads a file from MinIO.
     *
     * @param fileName the name of the file to download
     * @return the InputStream of the downloaded file
     */
    InputStream download(String fileName);

    /**
     * Deletes a file from MinIO.
     *
     * @param fileName the name of the file to delete
     */
    void delete(String fileName);

    /**
     * Lists all files stored in MinIO.
     *
     * @return a List of all files stored in MinIO
     */
    List<Object> list();
}
