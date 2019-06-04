//package com.callan.service.provider.config;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Map;
//import java.util.Properties;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.stereotype.Component;
//
//import com.callan.service.provider.pojo.cache.NativeSqlData;
//
//@Component
//public class OverTimeDataClear {
//
//
//	static int timeDayInt;
//
//	static {
//		String timeDayStr = null;
//		try {
//			InputStream is = OverTimeDataClear.class.getResourceAsStream("/application.yml");
//			Properties props = new Properties();
//			props.load(is);
//			timeDayStr = props.getProperty("sqlData.overTime.timeDay");
//			timeDayInt = Integer.parseInt(timeDayStr == null ? "10" : timeDayStr);
//		} catch (IOException e) {
//			e.printStackTrace();
//			timeDayInt = 10;
//		}
//		
//	}
//
//	@PostConstruct
//	public void start() {
//		JLPLog log = ThreadPoolConfig.getBaseContext();
//		(Executors.newScheduledThreadPool(1)).scheduleAtFixedRate(new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Map<String, Map<String, Object>> allDataMap = NativeSqlData.getAllData();
//					for (String key : allDataMap.keySet()) {
//						Map<String, Object> map = allDataMap.get(key);
//						
//						//如果数据状态不是完成，则跳过
//						if(!"finish".equals(map.get("status"))) {
//							continue;
//						}
//						
//						Date lastActiveTime = (Date) map.get("lastActiveTime");
//						Calendar cal = Calendar.getInstance();
//						cal.setTime(new Date());
//						cal.add(Calendar.DAY_OF_YEAR, -timeDayInt);
//						if (lastActiveTime.before(cal.getTime())) {
//							log.info("key : " + key + " ,lastActiveTime : " + lastActiveTime + " has idel more than "
//									+ timeDayInt + " minute, it will be clear...");
//							allDataMap.remove(key);
//						}
//					}
//				}catch(Exception e) {
//					log.error(e);
//				}
//			}
//		}), timeDayInt, timeDayInt, TimeUnit.MINUTES);
//	}
//}
