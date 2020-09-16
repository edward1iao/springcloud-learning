package com.edward1iao.springcloud.hystrixservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HystrixDataController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.data-service-url}")
    private String dataServiceUrl;

    @GetMapping("/getSpringProfilesActive")
    @HystrixCommand(fallbackMethod = "getSpringProfilesActiveFallback")
    public String getSpringProfilesActive(){
        return restTemplate.getForObject(dataServiceUrl+"/getSpringProfilesActive",String.class);
    }

    public String getSpringProfilesActiveFallback(){
        return "fallback--";
    }

}
