package com.callan.service.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAutoConfiguration
@MapperScan("com.callan.service.provider.dao.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class JLPServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(JLPServiceProviderApplication.class, args);
    }

}
