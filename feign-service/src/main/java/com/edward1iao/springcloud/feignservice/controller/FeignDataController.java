package com.edward1iao.springcloud.feignservice.controller;

import com.edward1iao.springcloud.feignservice.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignDataController {
    @Autowired
    private DataService dataService;

    @GetMapping("/getSpringProfilesActive")
    public String getSpringProfilesActive(){
        return dataService.getSpringProfilesActive();
    }
}
