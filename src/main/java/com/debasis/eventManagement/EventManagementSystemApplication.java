package com.debasis.eventManagement;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:5173")
public class EventManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagementSystemApplication.class, args);
    }

}