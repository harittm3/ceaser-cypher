package com.dsp.caesar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Caesar Cipher Spring Boot application.
 *
 * Starts an embedded Tomcat server that serves both the REST API
 * (under /api/*) and the static frontend from resources/static/.
 */
@SpringBootApplication
public class CaesarApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaesarApplication.class, args);
    }
}




