package com.albert.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * The `Application` class is the entry point for running the Spring Boot
 * application.
 * It starts the application and runs it using the `SpringApplication.run`
 * method.
 */

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
