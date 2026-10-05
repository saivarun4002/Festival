package com.vinayakachaviti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class VinayakaChavitiApplication {
    public static void main(String[] args) {
        SpringApplication.run(VinayakaChavitiApplication.class, args);
    }
}