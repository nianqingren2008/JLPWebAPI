package com.callan.service.provider.config;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/api/*",filterName = "jlpFilter")
public class JLPFilter implements Filter{

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String reqSerialNo = UUID.randomUUID().toString().replaceAll("-", "");
		HttpServletRequest req = (HttpServletRequest) request;
		String url = req.getRequestURI();
		String serviceName = url.substring(url.lastIndexOf("/")+1,url.length());
		JLPLog log = JLPLog.getInstance(serviceName,reqSerialNo);
		ThreadPoolConfig.setBaseContext(log);
		chain.doFilter(request, response);
		ThreadPoolConfig.clearBaseContext();
	}

	@Override
    public void destroy() {

    }
	
}
