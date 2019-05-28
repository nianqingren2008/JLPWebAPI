package com.callan.service.provider.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.callan.service.provider.pojo.cache.NativeSqlData;

@Component
public class OverTimeDataClear {

	Log logger = LogFactory.getLog(OverTimeDataClear.class);

	static int timeMinuteInt;

	static {
		String timeMinuteStr = null;
		try {
			InputStream is = OverTimeDataClear.class.getResourceAsStream("/application.yml");
			Properties props = new Properties();
			props.load(is);
			timeMinuteStr = props.getProperty("sqlData.overTime.timeMinute");
			timeMinuteInt = Integer.parseInt(timeMinuteStr == null ? "60" : timeMinuteStr);
		} catch (IOException e) {
			e.printStackTrace();
			timeMinuteInt = 60;
		}
		
	}

	@PostConstruct
	public void start() {
		(Executors.newScheduledThreadPool(1)).scheduleAtFixedRate(new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Map<String, Map<String, Object>> allDataMap = NativeSqlData.getAllData();
					for (String key : allDataMap.keySet()) {
						Map<String, Object> map = allDataMap.get(key);
						Date lastActiveTime = (Date) map.get("lastActiveTime");
						Calendar cal = Calendar.getInstance();
						cal.setTime(new Date());
						cal.add(Calendar.MINUTE, -timeMinuteInt);
						if (lastActiveTime.before(cal.getTime())) {
							logger.info("key : " + key + " ,lastActiveTime : " + lastActiveTime + " has idel more than "
									+ timeMinuteInt + " minute, it will be clear...");
							allDataMap.remove(key);
						}
					}
				}catch(Exception e) {
					logger.error(e);
				}
			}
		}), timeMinuteInt, timeMinuteInt, TimeUnit.MINUTES);
	}
}
