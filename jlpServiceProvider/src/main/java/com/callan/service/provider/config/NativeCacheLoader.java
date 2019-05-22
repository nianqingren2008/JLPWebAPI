package com.callan.service.provider.config;

import java.util.concurrent.ExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.service.IJRightService;
import com.callan.service.provider.service.IJSensitiveWordService;
import com.callan.service.provider.service.IJShowDetailViewService;
import com.callan.service.provider.service.IJShowViewService;
import com.callan.service.provider.service.IJTableDictService;
import com.callan.service.provider.service.IJTableFieldDictService;

@Service
public class NativeCacheLoader implements InitializingBean{
	
	@Autowired IJRightService jRightService;
	@Autowired IJSensitiveWordService jSensitiveWordService;
	@Autowired IJShowDetailViewService jShowDetailViewService;
	@Autowired IJShowViewService jShowViewService;
	@Autowired IJTableDictService jTableDictService;
	@Autowired IJTableFieldDictService jTableFieldDictService;
	@Autowired ExecutorService executorService;
	
	Log logger = LogFactory.getLog(NativeCacheLoader.class);
	
	@Override
	public void afterPropertiesSet() {
		logger.info("-----开始初始化内存数据-----");
		try {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					jRightService.getAll4Id();
					
					jSensitiveWordService.getAll(true);
					
					jShowDetailViewService.getAll4Id();
					jShowDetailViewService.getAll4ViewId();
					jShowDetailViewService.getAll4ViewId(true);
					
					jShowViewService.getAll4Id();
					
					jTableDictService.getAll4Code();
					jTableDictService.getAll4Id();
					jTableDictService.getAll4Name();
					
					jTableFieldDictService.getAll4Id();
					jTableFieldDictService.getAll4TableCode();
					jTableFieldDictService.getAll4TableCode(false);
				}
			});
			
		}catch(Throwable t) {
			logger.error("初始化内存数据出错",t);
		}
		logger.info("-----内存数据初始化完毕------");
	}

}
