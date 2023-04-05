package com.albert.app.model;

import java.net.URLEncoder;

/**
 * Represents a stored object.
 */
public class StoredObject {

    private String name;
    private String ipAddress = System.getenv("IP_ADDRESS");
    private String port = System.getenv("SPRING_BOOT_PORT");
    private String url;

    /**
     * Constructs a new StoredObject instance with the given name.
     *
     * @param name the name of the stored object.
     */

    public StoredObject(String name) throws Exception {
        this.name = name;
        String encodedFileName = URLEncoder.encode(name, "UTF-8").replace("+", "%20");
        this.url = String.format("http://%s:%s/objects/%s", ipAddress, port, encodedFileName);

    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
