package com.albert.app.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UrlGeneratorTest {

    @Test
    public void testGenerateUrl() throws Exception {
        String ipAddress = "127.0.0.1";
        String port = "8080";
        String name = "test object";

        UrlGenerator urlGenerator = new UrlGenerator(ipAddress, port, name);

        String expectedUrl = "http://127.0.0.1:8080/objects/test%20object";
        String actualUrl = urlGenerator.getUrl();

        assertEquals(expectedUrl, actualUrl);
    }
}
