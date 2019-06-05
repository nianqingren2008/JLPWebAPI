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
import javax.servlet.http.HttpServletResponse;

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
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = req.getRequestURI();
		if(url.contains("/api/")) {
			String[] tmp = url.split("/api/");
			String serviceName = null;
			if(tmp.length > 0) {
				serviceName = tmp[1].substring(0,tmp[1].indexOf("/") == -1 ? tmp[1].length() : tmp[1].indexOf("/") );
			}
//			String serviceName = url.substring(url.lastIndexOf("/")+1,url.length());
			if(serviceName != null) {
				JLPLog log = JLPLog.getInstance(serviceName,reqSerialNo);
				ThreadPoolConfig.setBaseContext(log);
			}
		}
		chain.doFilter(req, resp);
		ThreadPoolConfig.clearBaseContext();
	}

	@Override
    public void destroy() {

    }
	
}
