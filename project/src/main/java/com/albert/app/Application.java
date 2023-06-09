package com.albert.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Starts the application.
 */

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class Application {
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
