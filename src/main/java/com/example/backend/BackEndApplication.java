package com.example.backend;

import com.example.backend.Services.IImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.annotation.Resource;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableScheduling
public class BackEndApplication /*implements CommandLineRunner*/ {

    @Resource
    private IImageStorageService imageStorageService;
    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

    // create a CommandLineRunner bean that calls the init() method of the ImageStorageService class when the application starts up
//    @Override
//    public void run(String... args) throws Exception {
//        imageStorageService.init();
//    }
}
