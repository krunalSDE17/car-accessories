package com.kgawade.caraccessories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Car Accessories Management REST API.
 *
 * This is a Spring Boot rebuild of the original NetBeans/Swing desktop app
 * (CAD_Info.java). The original stored data only in a JTable's
 * DefaultTableModel, with no real persistence. This version adds a proper
 * REST API backed by a real database (H2 for quick local testing, MySQL
 * for production), replacing the Swing UI with HTTP endpoints that a
 * future web frontend (HTML/React) can call.
 */
@SpringBootApplication
public class CarAccessoriesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarAccessoriesApplication.class, args);
    }
}
