package com.eventapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the entry point of the Spring Boot application.
 * Running this class' main() method starts the whole backend server.
 */
@SpringBootApplication
public class EventManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagementApplication.class, args);
        System.out.println("=======================================");
        System.out.println(" Event Management System backend started");
        System.out.println(" API base URL: http://localhost:8080/api");
        System.out.println("=======================================");
    }
}
