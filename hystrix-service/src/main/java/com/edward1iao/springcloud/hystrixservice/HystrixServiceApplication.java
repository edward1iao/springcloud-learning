package com.edward1iao.springcloud.hystrixservice;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@SpringBootApplication
//@EnableDiscoveryClient
//@EnableCircuitBreaker
@SpringCloudApplication
public class HystrixServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixServiceApplication.class,args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * hystrix dashboard面板监控时才需要以下配置
     * 暴露hystrix监控端点
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
        servletRegistrationBean.addUrlMappings("/actuator/hystrix.stream");
        return servletRegistrationBean;
    }
}
