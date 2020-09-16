package com.edward1iao.springcloud.feignservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "data-service",fallback = DataFallbackService.class)
public interface DataService {
    @GetMapping("/getSpringProfilesActive")
    String getSpringProfilesActive();
}
