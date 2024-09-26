package com.cxl;

import com.cxl.email.EmailBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmailApp {
    public static void main(String[] args) {
        SpringApplication.run(EmailApp.class);
    }
}
