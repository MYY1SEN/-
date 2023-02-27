package com.myq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.myq.mapper")
public class ServicePassenger {
    public static void main(String[] args) {
        SpringApplication.run(ServicePassenger.class);
    }
}
