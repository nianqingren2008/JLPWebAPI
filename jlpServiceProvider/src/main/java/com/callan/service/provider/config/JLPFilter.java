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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.service.IJUserService;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/api/*",filterName = "jlpFilter")
public class JLPFilter implements Filter{
	@Autowired
	private IJUserService userService;
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
		if("OPTIONS".equalsIgnoreCase(req.getMethod())) {
			chain.doFilter(req, resp);
		}else {
		if(url.contains("/api/")) {
			if(!"/api/User".equals(url) 
				 && !url.startsWith("/api/DownLoad/image")) {
				String authorization = req.getHeader("Authorization") == null ? ""
						: req.getHeader("Authorization");
				if(StringUtils.isBlank(authorization)) {
					resp.setStatus(999);
					return;
				}
				JUser user = userService.getUserByToken(authorization);
				if(user == null || user.getId().longValue() == 0) {
					resp.setStatus(999);
					return;
				}
			}
			
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
	}

	@Override
    public void destroy() {

    }
	
}
