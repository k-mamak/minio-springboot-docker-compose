package com.albert.app.model;

public class StoredObject {

    public String name;

    public String url;

    /**
     * Sets the URL of the file based on its name.
     */
    public void setUrl() {
        this.url = String.format("http://%s:8080/files/%s", System.getenv("PUBLIC_ADRESS"), name);
    }
}
