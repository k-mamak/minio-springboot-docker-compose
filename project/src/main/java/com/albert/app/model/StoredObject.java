package com.albert.app.model;

import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;

/**
 * Represents a stored object.
 */
public class StoredObject {

    private String name;
    private String port = System.getenv("SPRING_BOOT_PORT");
    private String url;

    /**
     * Checks if a given port is available on a host and returns a boolean.
     *
     * @param host the host to check
     * @param port the port to check
     * @return true if the port is available, false otherwise
     */

    private static boolean isPortAvailable(String host, int port) {
        try (Socket ignored = new Socket(host, port)) {
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Constructs a new StoredObject instance with the given name.
     *
     * @param name the name of the stored object.
     */

    public StoredObject(String name) {
        try {

            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            this.name = name;
            this.url = String.format("http://%s:%s/objects/%s", isPortAvailable(ipAddress,
                    Integer.parseInt(port)) ? ipAddress : "localhost", port, name);
        } catch (IOException e) {
            // ignore
        }
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
