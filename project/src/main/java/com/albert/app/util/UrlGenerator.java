package com.albert.app.util;

import java.net.URLEncoder;

/**
 * Represents objects name and url.
 */
public class UrlGenerator {

    /**
     * The IP address of the stored object.
     */
    private String ipAddress;
    /**
     * The port of the stored object.
     */
    private String port;
    /**
     * The name of the stored object.
     */
    private String name;
    /**
     * The url of the stored object.
     */
    private String url;

    /**
     * Constructs a new UrlGenerator instance with the given name,
     * ip address and port.
     *
     * @param ipAddressValue the ip address of the stored object.
     * @param portValue      the port of the stored object.
     * @param objectName     the name of the stored object.
     */
    public UrlGenerator(final String ipAddressValue,
            final String portValue,
            final String objectName)
            throws Exception {
        this.ipAddress = ipAddressValue;
        this.port = portValue;
        this.name = objectName;
        this.url = generateUrl();
    }

    /**
     * Returns the name of the stored object.
     *
     * @return the name of the stored object.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the URL of the stored object.
     *
     * @return the URL of the stored object.
     */
    public String getUrl() {
        return url;
    }

    private String generateUrl() throws Exception {
        String encodedName = URLEncoder.encode(name, "UTF-8")
                .replace("+", "%20");
        return String.format("http://%s:%s/objects/%s",
                ipAddress,
                port,
                encodedName);
    }
}
