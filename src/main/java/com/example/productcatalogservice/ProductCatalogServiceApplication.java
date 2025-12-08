package com.example.productcatalogservice;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProductCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductCatalogServiceApplication.class, args);
    }

    @PostConstruct
    public void printDbUrl() {
        System.out.println("DB_URL = " + System.getenv("DB_URL"));
    }


}
