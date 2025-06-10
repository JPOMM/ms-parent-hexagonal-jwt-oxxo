package com.oxxo.scr.ms.maintance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ScrMsMaintanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrMsMaintanceApplication.class, args);
    }

}
