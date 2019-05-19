package com.callan.service.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
@MapperScan("com.callan.service.provider.dao.mapper")
public class JLPServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(JLPServiceProviderApplication.class, args);
    }

}
