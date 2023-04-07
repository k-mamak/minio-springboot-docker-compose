package com.albert.app.controller;

import com.albert.app.service.ObjectStorageService;
import com.albert.app.util.UrlGenerator;
import com.albert.app.factory.ObjectStorageServiceFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /**
     * The ObjectStorageService instance used by this controller.
     */
    private final ObjectStorageService objectStorageService;

    /**
     * Constructs a new ObjectStorageController instance.
     *
     * @param objectStorageServiceFactory the factory used to create an instance
     *                                    of ObjectStorageService
     */
    @Autowired
    public ObjectStorageController(
            final ObjectStorageServiceFactory objectStorageServiceFactory) {
        this.objectStorageService = objectStorageServiceFactory
                .getObjectStorageService();
    }

    /**
     * GET requests for downloading a specific object. It takes
     * the object name as a path variable and writes the contents of the object
     * to the response's output stream.
     *
     * @param object   the object name
     * @param response the HTTP response object
     */

    @GetMapping("/objects/{object}")
    public void downloadObject(@PathVariable("object") final String object,
            final HttpServletResponse response) throws Exception {
        String decodedObjectName = URLDecoder.decode(object, "UTF-8");
        try (InputStream inputStream = objectStorageService.getObject(object)) {
            response.addHeader(
                    "Content-disposition",
                    "attachment;filename=" + decodedObjectName);
            response.setContentType(
                    URLConnection.guessContentTypeFromName(object));

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
     * @return the URL and status code of the uploaded object.
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadObject(
            @RequestParam("file") final MultipartFile object) throws Exception {
        String url = objectStorageService.storeObject(object.getInputStream(),
                object.getOriginalFilename(),
                object.getContentType());
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    /**
     * DELETE requests for deleting an object. It takes the object
     * name as a path variable and deletes the object from the storage service
     * using the `ObjectStorageService` component.
     *
     * @param object the object name
     * @return a status code.
     */
    @DeleteMapping("/delete/{object}")
    public ResponseEntity<Void> deleteObject(
            @PathVariable("object") final String object) throws Exception {
        String decodedObjectName = URLDecoder.decode(object, "UTF-8");
        objectStorageService.deleteObject(decodedObjectName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET requests for listing all the objects in the storage
     * service. It uses the `ObjectStorageService` component to get a list of
     * name and URL orl all the objects.
     *
     * @return a list of name and URL and a status code.
     */
    @GetMapping("/objects")
    public ResponseEntity<List<Map<String, String>>> listObjects() {
        List<UrlGenerator> urlGenerators = objectStorageService.getListObject();
        List<Map<String, String>> objects = new ArrayList<>();

        for (UrlGenerator urlGenerator : urlGenerators) {
            Map<String, String> object = new HashMap<>();
            object.put("name", urlGenerator.getName());
            object.put("url", urlGenerator.getUrl());
            objects.add(object);
        }

        return new ResponseEntity<>(objects, HttpStatus.OK);
    }

}
