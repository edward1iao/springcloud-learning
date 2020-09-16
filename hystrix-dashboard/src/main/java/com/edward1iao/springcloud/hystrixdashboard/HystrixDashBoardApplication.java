package com.edward1iao.springcloud.hystrixdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * desc: Hystrix断路器仪表盘启动器
 * author: edward1iao
 * date: 2020/9/16
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
public class HystrixDashBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashBoardApplication.class,args);
    }
}
