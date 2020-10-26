package com.edward1iao.springcloud.nacosdataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosDataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosDataServiceApplication.class,args);
    }
}
