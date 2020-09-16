package com.edward1iao.springcloud.dataservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @GetMapping("/getSpringProfilesActive")
    public String getSpringProfilesActive(){
        return "springProfilesActive:"+springProfilesActive;
    }
}
