package com.albert.app.service.s3;

import com.albert.app.model.StoredObject;
import com.albert.app.service.ObjectStorageService;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service("s3BucketService")
public class S3BucketService implements ObjectStorageService {

    @Override
    public String storeObject(InputStream inputStream, String objectName, String contentType) {
        System.out.println("Storing object in S3 bucket...");
        return "https://s3-bucket.com/" + objectName;
    }

    @Override
    public InputStream getObject(String objectName) {
        System.out.println("Downloading object from S3: " + objectName);
        return null;
    }

    @Override
    public void deleteObject(String objectName) {
        System.out.println("Deleting object from S3: " + objectName);
    }

    @Override
    public List<StoredObject> getListObject() {
        System.out.println("Listing objects from S3 bucket");
        List<StoredObject> objects = new ArrayList<>();
        return objects;
    }
}
