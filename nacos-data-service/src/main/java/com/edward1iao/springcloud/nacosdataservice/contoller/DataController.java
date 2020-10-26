package com.edward1iao.springcloud.nacosdataservice.contoller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class DataController {
    private Logger logger = LoggerFactory.getLogger(DataController.class);

//    @Value("${spring.profiles.active}")
    private String springProfilesActive="a";

    /**
     * 从nacos获取配置，需要给予默认值，通过refreshScope进行刷新值
     */
    @Value("${usera.namea:aaaa}")
    private String testData;

    @GetMapping("/getSpringProfilesActive")
    public String getSpringProfilesActive(){
        logger.info("=====getSpringProfilesActive 服务被调用======");
        return "springProfilesActive:"+springProfilesActive+",testData:"+testData;
    }
}
