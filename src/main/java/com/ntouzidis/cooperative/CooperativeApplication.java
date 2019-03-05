package com.ntouzidis.cooperative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CooperativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CooperativeApplication.class, args);
    }

    @Bean(name = "multiExecutor")
    public ExecutorService multiExecutor() {
        return Executors.newFixedThreadPool(20);
    }

    @Bean(name ="restTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
