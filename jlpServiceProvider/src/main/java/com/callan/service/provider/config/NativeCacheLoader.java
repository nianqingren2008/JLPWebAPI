package com.callan.service.provider.config;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callan.service.provider.service.IJFiletypeService;
import com.callan.service.provider.service.IJRightService;
import com.callan.service.provider.service.IJRoleRightService;
import com.callan.service.provider.service.IJSensitiveWordService;
import com.callan.service.provider.service.IJShowDetailViewService;
import com.callan.service.provider.service.IJShowViewService;
import com.callan.service.provider.service.IJSystemConfigService;
import com.callan.service.provider.service.IJTableDictService;
import com.callan.service.provider.service.IJTableFieldDictService;
import com.callan.service.provider.service.IJUserService;

@Service
public class NativeCacheLoader implements InitializingBean{
	
	@Autowired IJRightService jRightService;
	@Autowired IJSensitiveWordService jSensitiveWordService;
	@Autowired IJShowDetailViewService jShowDetailViewService;
	@Autowired IJShowViewService jShowViewService;
	@Autowired IJTableDictService jTableDictService;
	@Autowired IJTableFieldDictService jTableFieldDictService;
	@Autowired ExecutorService executorService;
	@Autowired IJUserService userService;
	@Autowired IJRoleRightService roleRightService;
	@Autowired IJSystemConfigService systemConfigService;
	
	@Autowired IJFiletypeService filetypeSerice;
	
	@Override
	public void afterPropertiesSet() {
		JLPLog log = ThreadPoolConfig.getBaseContext();
//		log.info("-----开始初始化内存数据-----");
		try {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					jRightService.getAll4Id();
					
					jSensitiveWordService.getAll();
					jSensitiveWordService.getAll4Name();
					
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
					
					userService.getAll4Id();
					
					roleRightService.getAll4RoleId();
					
					systemConfigService.getAll();
					
					filetypeSerice.getAll();
					
					
					//TODO 程序启动，扫描数据状态处于loading状态的key，并清理掉它
					
				}
			});
			
		}catch(Throwable t) {
			log.error("初始化内存数据出错",t);
		}
//		log.info("-----内存数据初始化完毕------");
	}

}
