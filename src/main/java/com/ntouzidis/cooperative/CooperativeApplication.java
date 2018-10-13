package com.ntouzidis.cooperative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CooperativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CooperativeApplication.class, args);
    }
}
