package com.albert.app.controller;

import com.albert.app.service.ObjectStorageService;
import com.albert.app.util.UrlGenerator;
import com.albert.app.factory.ObjectStorageServiceFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.lang.Exception;
import java.net.URLConnection;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * The `ObjectStorageController` class is a Spring Boot controller that handles
 * HTTP requests related to object management. It has several methods that
 * handle different types of requests, such as uploading, downloading, listing,
 * and deleting objects.
 */

@RestController
@RequestMapping
public class ObjectStorageController {

    private final ObjectStorageService objectStorageService;

    @Autowired
    public ObjectStorageController(ObjectStorageServiceFactory objectStorageServiceFactory) {
        this.objectStorageService = objectStorageServiceFactory.getObjectStorageService();
    }

    /**
     * GET requests for downloading a specific object. It takes
     * the object name as a path variable and writes the contents of the object to
     * the response's output stream.
     * 
     * @param object   the object name
     * @param response the HTTP response object
     */
    @GetMapping("/objects/{object}")
    public void downloadObject(@PathVariable("object") String object, HttpServletResponse response) throws Exception {
        String decodedObjectName = URLDecoder.decode(object, "UTF-8");
        try (InputStream inputStream = objectStorageService.getObject(object)) {
            response.addHeader("Content-disposition", "attachment;filename=" + decodedObjectName);
            response.setContentType(URLConnection.guessContentTypeFromName(object));

            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        }
    }

    /**
     * POST requests for uploading an object. It takes the object as
     * a request parameter and uploads it to the storage service using the
     * `ObjectStorageService` component. It returns URL of the uploaded object.
     *
     * @param object the uploaded object
     * @return the URL of the uploaded object and an HTTP status code of OK (200)
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadObject(@RequestParam("file") MultipartFile object) throws Exception {
        String url = objectStorageService.storeObject(object.getInputStream(), object.getOriginalFilename(),
                object.getContentType());
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    /**
     * DELETE requests for deleting an object. It takes the object
     * name as a path variable and deletes the object from the storage service using
     * the `ObjectStorageService` component.
     *
     * @param object the object name
     * @return a HTTP status code of OK (200)
     */
    @DeleteMapping("/delete/{object}")
    public ResponseEntity<Void> deleteObject(@PathVariable("object") String object) throws Exception {
        String decodedObjectName = URLDecoder.decode(object, "UTF-8");
        objectStorageService.deleteObject(decodedObjectName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET requests for listing all the objects in the storage
     * service. It uses the `ObjectStorageService` component to get a list of
     * `UrlGenerator` representing all the objects name and URL.
     *
     * @return a list of `UrlGenerator` and an HTTP status code of OK (200)
     */
    @GetMapping("/objects")
    public ResponseEntity<List<UrlGenerator>> listObjects() {
        List<UrlGenerator> metadata = objectStorageService.getListObject();
        return new ResponseEntity<>(metadata, HttpStatus.OK);
    }
}
