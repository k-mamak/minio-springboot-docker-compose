package com.albert.app.controller;

import com.albert.app.model.Object;
import com.albert.app.service.ObjectStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.lang.Exception;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * The `ObjectStorageController` class is a Spring Boot controller that handles
 * HTTP requests related to file management. It has several methods that handle
 * different types of requests, such as uploading, downloading, listing, and
 * deleting files.
 */

@RestController
@RequestMapping
public class ObjectStorageController {

    @Autowired
    private ObjectStorageService object_storage_service;

    /**
     * This method handles GET requests for downloading a specific file. It takes
     * the file name as a path variable and writes the contents of the file to the
     * response's output stream.
     * 
     * @param object   the file name
     * @param response the HTTP response object
     */
    @GetMapping("/files/{object}")
    public void getObject(@PathVariable("object") String object, HttpServletResponse response) throws Exception {
        try {
            InputStream inputStream = object_storage_service.download(object);

            response.addHeader("Content-disposition", "attachment;filename=" + object);
            response.setContentType(URLConnection.guessContentTypeFromName(object));

            // Copy the stream to the response's output stream.
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            throw new Exception("Failed to download file", e);
        }
    }

    /**
     * This method handles POST requests for uploading a file. It takes the file as
     * a request parameter and uploads it to the storage service using the
     * `ObjectStorageService` component. It returns URL of the uploaded file.
     *
     * @param file the uploaded file
     * @return the URL of the uploaded file and an HTTP status code of OK (200)
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        try {
            String url = object_storage_service.upload(file.getInputStream(), file.getOriginalFilename(),
                    file.getContentType());
            return new ResponseEntity<>(url, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("Failed to upload file", e);
        }
    }

    /**
     * This method handles DELETE requests for deleting a file. It takes the file
     * name as a path variable and deletes the file from the storage service using
     * the `ObjectStorageService` component.
     *
     * @param object the file name
     * @return a HTTP status code of OK (200)
     */
    @DeleteMapping("/delete/{object}")
    public ResponseEntity<Void> deleteFile(@PathVariable("object") String object) throws Exception {
        try {
            object_storage_service.delete(object);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("Failed to delete file", e);
        }
    }

    /**
     * This method handles GET requests for listing all the files in the storage
     * service. It uses the `ObjectStorageService` component to get a list of
     * `Object` representing all the files.
     *
     * @return a list of `Object` and an HTTP status code of OK (200)
     */
    @GetMapping("/files")
    public ResponseEntity<List<Object>> listFiles() throws Exception {
        try {
            List<Object> files = object_storage_service.list();
            return new ResponseEntity<>(files, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("Failed to get files", e);
        }

    }
}
