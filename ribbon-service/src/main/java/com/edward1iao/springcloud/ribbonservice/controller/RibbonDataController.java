package com.edward1iao.springcloud.ribbonservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RibbonDataController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.data-service-url}")
    private String dataServiceUrl;

    @GetMapping("/getSpringProfilesActive")
    public String getSpringProfilesActive(){
        return restTemplate.getForObject(dataServiceUrl+"/getSpringProfilesActive",String.class);
    }

}
