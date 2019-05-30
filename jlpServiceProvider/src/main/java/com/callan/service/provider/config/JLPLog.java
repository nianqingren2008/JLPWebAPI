package com.callan.service.provider.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class JLPLog extends Logger {

	private static ConcurrentMap<String, Logger> map = new ConcurrentHashMap<String, Logger>();
	public Logger logger = null;
	private String reqSerialNo = null;

	public static JLPLog getInstance(String serviceName, String reqSerialNo) {
		JLPLog acctLog = new JLPLog(serviceName, reqSerialNo);
		return acctLog;
	}

	private JLPLog(String serviceName, String reqSerialNo) {

		super(serviceName);
		this.reqSerialNo = reqSerialNo;
		synchronized (map) {
			if (StringUtils.isNotBlank(serviceName)) {
				logger = map.get(serviceName);
				if (logger == null) {
					logger = initAcctLogger(serviceName);
					map.put(serviceName, logger);
				}
			} else {
				logger = map.get("jlp");
				if (logger == null) {
					logger = initAcctLogger(serviceName);
					map.put("jlp", logger);
				}
			}
			logger.setLevel(Level.INFO);
		}

	}

	private synchronized Logger initAcctLogger(String serviceName) {
		logger = map.get(serviceName);
		if (logger != null) {
			return logger;
		}
		Logger log = Logger.getLogger(serviceName);
		log.removeAllAppenders();
		// 设置Logger是否在父Logger的appender里输出，默认为true
		log.setAdditivity(false);
		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss,SSS} %p %m%n");
		String logFullPath = null;
		Appender appender = null;
		try {
			String confFilePath = getLocalPath();
			logFullPath = getLogFullPath(serviceName, confFilePath);
			System.out.println("-------------logFullPath-----------"+logFullPath);
			appender = new DailyRollingFileAppender(layout, logFullPath, "yyyy-MM-dd");
			appender.setName("jlpLogAppender");
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.addAppender(appender);

		ConsoleAppender consoleAppender = new ConsoleAppender(layout, "System.out");
		log.addAppender(consoleAppender);

		return log;
	}

	private String getLogFullPath(String serviceName, String confFilePath) {
		String logFullPath = "";
		String logName = null;
		if (StringUtils.isBlank(serviceName)) {
			logName = "jlp.log";
		} else {
			logName = serviceName + ".log";
		}
		logFullPath = confFilePath + "/jlpLogs/" + (serviceName == null ? "" : serviceName) + "/" + logName;
		
		String os = System.getProperty("os.name");
		if(os.toLowerCase().startsWith("win")){
			if(logFullPath.startsWith("/")) {
				logFullPath = logFullPath.substring(1,logFullPath.length());
			}
		} 
		if(logFullPath.startsWith("file:/")){
			logFullPath = logFullPath.substring(6,logFullPath.length());
		} 
		return logFullPath;
	}

	public static String getLocalPath() {
		String confFilePath = JLPLog.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		int end = confFilePath.lastIndexOf(".jar");
		if(end == -1) {
			end = confFilePath.length();
		}
		confFilePath = confFilePath.substring(0, end);
		int firstIndex = confFilePath.lastIndexOf(System.getProperty("path.separator")) + 1;
		int lastIndex = confFilePath.lastIndexOf("/") + 1;
		confFilePath = confFilePath.substring(firstIndex, lastIndex);
		return confFilePath;
	}

	@Override
	public void debug(Object message) {
		if (message == null) {
			message = "";
		}
		logger.debug(reqSerialNo + " " + message);
	}

	@Override
	public void debug(Object message, Throwable t) {
		if (message == null) {
			message = "";
		}
		logger.debug(reqSerialNo + " " + message, t);
	}

	@Override
	public void error(Object message) {
		if (message == null) {
			message = "";
		}
		logger.error(reqSerialNo + " " + message);
	}

	@Override
	public void error(Object message, Throwable t) {
		if (message == null) {
			message = "";
		}

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		String msg = sw.toString();
		String[] msgArr = msg.split("\n");
		logger.error(reqSerialNo + " " + message);
		for (String str : msgArr) {
			logger.error(reqSerialNo + /* " stackTrace:" + */ "  " + str.replaceAll("\r", ""));
		}
	}

	@Override
	public void info(Object message) {
		if (message == null) {
			message = "";
		}
		logger.info(reqSerialNo + " " + message);
	}

	@Override
	public void info(Object message, Throwable t) {
		if (message == null) {
			message = "";
		}
		logger.info(reqSerialNo + " " + message, t);
	}

	@Override
	public void warn(Object message) {
		if (message == null) {
			message = "";
		}
		logger.warn(reqSerialNo + " " + message);
	}

	@Override
	public void warn(Object message, Throwable t) {
		if (message == null) {
			message = "";
		}
		logger.warn(reqSerialNo + " " + message, t);
	}
public static void main(String[] args) {
	String file = "file:/D:/javaJlp//jlpLogs/jlp/jlp.log";
	System.out.println(file.substring(6,file.length()));
}
}
