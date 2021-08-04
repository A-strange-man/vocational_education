package com.example.online_class;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class OnlineClassApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineClassApplication.class, args);
    }

}
