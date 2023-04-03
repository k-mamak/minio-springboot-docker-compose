package com.albert.app.service.s3;

import com.albert.app.model.StoredObject;
import com.albert.app.service.ObjectStorageService;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service("s3BucketService")
public class S3BucketService implements ObjectStorageService {

    @Override
    public String store_object(InputStream inputStream, String fileName, String contentType) {
        System.out.println("Storing object in S3 bucket...");
        return "https://s3-bucket.com/" + fileName;
    }

    @Override
    public InputStream get_object(String fileName) {
        System.out.println("Downloading file from S3: " + fileName);
        return null;
    }

    @Override
    public void delete_object(String fileName) {
        System.out.println("Deleting file from S3: " + fileName);
    }

    @Override
    public List<StoredObject> objects_list() {
        System.out.println("Listing files from S3 bucket");
        List<StoredObject> objects = new ArrayList<>();
        return objects;
    }
}
