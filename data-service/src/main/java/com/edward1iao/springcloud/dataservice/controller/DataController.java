package com.edward1iao.springcloud.dataservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
    private Logger logger = LoggerFactory.getLogger(DataController.class);

    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @GetMapping("/getSpringProfilesActive")
    public String getSpringProfilesActive(){
        logger.info("=====getSpringProfilesActive 服务被调用======");
        return "springProfilesActive:"+springProfilesActive;
    }
}
