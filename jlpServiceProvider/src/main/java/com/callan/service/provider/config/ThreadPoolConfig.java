package com.callan.service.provider.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadPoolConfig {
	
	@Value("${executorService.threedMax}")
	private String threedMax;
	
	@Bean
    public ExecutorService getThreadPool(){
        return Executors.newFixedThreadPool(StringUtils.isBlank(threedMax) ? 100 : Integer.parseInt(threedMax));
    }
}
