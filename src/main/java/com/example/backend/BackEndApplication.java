package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class BackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

}
