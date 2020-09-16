package com.edward1iao.springcloud.feignservice.service;

import org.springframework.stereotype.Component;

@Component
public class DataFallbackService implements DataService{
    @Override
    public String getSpringProfilesActive() {
        return "feign-fallback-";
    }
}
