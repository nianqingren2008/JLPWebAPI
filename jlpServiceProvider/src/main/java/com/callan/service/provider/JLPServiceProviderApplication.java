package com.callan.service.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.callan.service.provider.config.ConnMedicalDb;

@SpringBootApplication
@EnableAutoConfiguration
@PropertySource({"classpath:application.yml","classpath:application-dev.yml","classpath:application-prod.yml"})
@MapperScan("com.callan.service.provider.dao.mapper")
@EnableAspectJAutoProxy(proxyTargetClass=true,exposeProxy = true)
public class JLPServiceProviderApplication {

    public static void main(String[] args) {
    	try {
    		SpringApplication.run(JLPServiceProviderApplication.class, args);
    		ConnMedicalDb.getConnection();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

}
