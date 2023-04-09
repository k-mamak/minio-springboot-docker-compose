package com.albert.app.controller;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;

import com.albert.app.service.minio.MinioService;
import com.albert.app.factory.ObjectStorageServiceFactory;
import com.albert.app.util.UrlGenerator;

public class ObjectStorageControllerTest {

    @Test
    public void testDownloadObject() throws Exception {
        // Create mock ObjectStorageServiceFactory.
        ObjectStorageServiceFactory factory = mock(ObjectStorageServiceFactory.class);

        // Create mock MinioService.
        MinioService service = mock(MinioService.class);

        // Create sample.
        byte[] data = "test data".getBytes();
        String objectName = "test object";
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();

        // Setup when getObject is called return data.
        when(service.getObject(objectName)).thenReturn(new ByteArrayInputStream(data));

        // Setup when getObjectStorageService is called, return mocked service.
        when(factory.getObjectStorageService()).thenReturn(service);

        // Initialize ObjectStorageController with mocked ObjectStorageServiceFactory.
        ObjectStorageController controller = new ObjectStorageController(factory);

        ResponseEntity<String> response = controller.downloadObject(objectName, mockResponse);

        assertEquals(mockResponse.getContentAsString(), ("test data"));
        assertEquals(mockResponse.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getBody(), "Downloaded successfully");
    }

    @Test
    public void testUploadObject() throws Exception {
        // Create mock ObjectStorageServiceFactory.
        ObjectStorageServiceFactory factory = mock(ObjectStorageServiceFactory.class);

        // Create mock MinioService.
        MinioService service = mock(MinioService.class);

        // Create sample.
        String objectName = "test object";
        String contentType = "application/octet-stream";
        byte[] data = "test data".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", objectName, contentType, data);
        UrlGenerator urlGenerator = new UrlGenerator("localhost", "8080", objectName);

        // Setup when storeObject is called, return url.
        when(service.storeObject(any(InputStream.class), any(String.class), eq(contentType)))
                .thenReturn(urlGenerator.getUrl());

        // Setup when getObjectStorageService is called, return mocked service.
        when(factory.getObjectStorageService()).thenReturn(service);

        // Initialize ObjectStorageController with mocked ObjectStorageServiceFactory.
        ObjectStorageController controller = new ObjectStorageController(factory);

        ResponseEntity<String> response = controller.uploadObject(file);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), ("http://localhost:8080/objects/test%20object"));
    }

    @Test
    public void testListObjects() throws Exception {
        // Create mock ObjectStorageServiceFactory.
        ObjectStorageServiceFactory factory = mock(ObjectStorageServiceFactory.class);

        // Create mock MinioService.
        MinioService service = mock(MinioService.class);

        // Create some sample objects to return
        List<UrlGenerator> sampleObjects = new ArrayList<>();
        sampleObjects.add(new UrlGenerator("localhost", "8080", "object1"));
        sampleObjects.add(new UrlGenerator("localhost", "8080", "object2"));

        // Set up the mock service to return the sample objects
        when(service.getListObject()).thenReturn(sampleObjects);

        // Setup when getObjectStorageService is called, return mocked service.
        when(factory.getObjectStorageService()).thenReturn(service);

        // Initialize ObjectStorageController with mocked ObjectStorageServiceFactory.
        ObjectStorageController controller = new ObjectStorageController(factory);

        // Call the listObjects method and get the response entity
        ResponseEntity<List<Map<String, String>>> responseEntity = controller.listObjects();

        // Check that the response entity has a 200 status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Check that the response entity contains the correct number of objects
        List<Map<String, String>> objects = responseEntity.getBody();
        assertEquals(sampleObjects.size(), objects.size());

        // Check that each object in the response entity has the correct name and URL
        for (int i = 0; i < sampleObjects.size(); i++) {
            Map<String, String> object = objects.get(i);
            UrlGenerator sampleObject = sampleObjects.get(i);
            assertEquals(sampleObject.getName(), object.get("name"));
            assertEquals(sampleObject.getUrl(), object.get("url"));
        }
    }

}