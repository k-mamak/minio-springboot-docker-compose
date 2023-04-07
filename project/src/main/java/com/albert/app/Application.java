package com.albert.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Starts the application.
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public final class Application {

    /**
     * Prevents instantiation of the {@code Application} class.
     */
    private Application() {
        // Utility classes should not have a public or default constructor.
    }

    /**
     * Runs the application.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
