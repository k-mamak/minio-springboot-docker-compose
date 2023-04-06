package com.albert.app.util;

import java.net.URLEncoder;

/**
 * Represents objects name and url.
 */
public class UrlGenerator {

    private String ipAddress;
    private String port;
    public String name;
    public String url;

    /**
     * Constructs a new UrlGenerator instance with the given name, ip address and
     * port.
     *
     * @param ipAddress the ip address of the stored object.
     * @param port      the port of the stored object.
     * @param name      the name of the stored object.
     */
    public UrlGenerator(String ipAddress, String port, String name)
            throws Exception {
        this.ipAddress = ipAddress;
        this.port = port;
        this.name = name;
        this.url = generateUrl();
    }

    private String generateUrl() throws Exception {
        String encodedName = URLEncoder.encode(name, "UTF-8").replace("+", "%20");
        return String.format("http://%s:%s/objects/%s", ipAddress, port, encodedName);
    }
}
