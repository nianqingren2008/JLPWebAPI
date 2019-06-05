package com.callan.service.provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.base.CacheResponse;
import com.callan.service.provider.pojo.cache.CacheKey;
import com.callan.service.provider.pojo.cache.LocalCacheable;
import com.callan.service.provider.pojo.cache.LocalCacheable.KeyMode;
import com.callan.service.provider.pojo.cache.LocalData;

@Aspect
@Component
public class LocalCacheAop {

	@Pointcut("@annotation(com.callan.service.provider.pojo.cache.LocalCacheable)")
	public void methodCachePointcut() {

	}

	/**
	 * （简单的key----value形式，以String方式存储） 从缓存获取数据，获取为空时，交由pjp处理，否则直接返回缓存数据
	 * 
	 * @param pjp
	 * @param cache
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Around(value = "methodCachePointcut()")
	public Object cached(ProceedingJoinPoint pjp) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Object value = null;
		LocalCacheable cache = null;
		String targetName = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		Object[] arguments = pjp.getArgs();
		try {
			Class targetClass = Class.forName(targetName);
			Method[] method = targetClass.getMethods();
			for (Method m : method) {
				if (m.getName().equals(methodName)) {
					Class[] tmpCs = m.getParameterTypes();
					if (tmpCs.length == arguments.length) {
						cache = m.getAnnotation(LocalCacheable.class);
						break;
					}
				}
			}
		} catch (ClassNotFoundException e2) {
			log.error(e2.getMessage(), e2);
		}

		String methonName = null;
		try {
			String key = cache.key();
			if (key == null || "".equals(key)) {
				key = getCacheKey(pjp, cache.key(), cache.keyMode());
			}
			// Boolean delFlag = cache.delFlag();
			methonName = pjp.getSignature().getName();

			// 从缓存获取，如果没有获取到，先执行原有方法，获取值，再把值缓存起来
			value = getFromCache(key, pjp);

		} catch (Throwable e) {
			log.error(methonName + ",从缓存读取失败 ,原因：" + e.getMessage(), e);
			e.printStackTrace();
			try {
				value = pjp.proceed();// 删除该业务点全部数据，继续执行原有方法
			} catch (Throwable e1) {
				log.error(methonName + ",继续执行原有方法失败 ,原因：" + e1.getMessage(), e1);
			}
		}
		return value;
	}

	private Object getFromCache(String key, ProceedingJoinPoint pjp) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		Object value = null;
		String declaringTypeName = pjp.getSignature().getDeclaringTypeName();
		declaringTypeName = declaringTypeName.substring(declaringTypeName.lastIndexOf(".") + 1,
				declaringTypeName.length());
		String methonName = declaringTypeName + "." + pjp.getSignature().getName();

//    	    logger.debug(methonName + ",from cache start ,  key:" + key);
		value = LocalData.getData(key); // 从缓存获取数据
		if (value != null) {
//    	    	logger.info(methonName + ",[GET SUCCESS],key:" + key);//+ "，value:" + value);
			return value; // 如果有数据,则直接返回
		}
		try {
			log.info(methonName + ",[GET FAIL] , will proceed");
			value = pjp.proceed(); // 跳过缓存,到后端查询数据

			if (value instanceof CacheResponse) {
				CacheResponse vo = (CacheResponse) value;
				if (vo.getCode() == 0) {
					log.info(methonName + ",excuter success,will cache result");
					LocalData.setData(key, value);
				} else {
					log.info(methonName + ",excuter fail,will not do cache");
				}
			} else if (value != null) {
				LocalData.setData(key, value);
			}

		} catch (Throwable e) {
			log.error(methonName + ",原有方法执行失败,原因：" + e.getMessage(), e);
			e.printStackTrace();
		}

		return value;
	}

	/**
	 * 获取缓存的key值 （使用sha1加密成32位） 对get()方法配置使用缓存,
	 * 缓存的key格式为:{package_name}.DemoServiceImpl.get
	 * 同时为参数配置了@CacheKey后,表示此参数的值将做为key的后缀,此例的key,最终是:{package_name}.DemoServiceImpl.get.{id}
	 * 可以为多个参数配置@CacheKey,拦截器会调用参数的toString()做为key的后缀
	 * 若配置多个@CacheKey参数,那么最终的key格式为:{package_name}.{class_name}.{method}.{arg1}.{arg2}.{...}
	 * 
	 * @param pjp
	 * @param cache
	 * @return
	 */
	private String getCacheKey(ProceedingJoinPoint pjp, String key, KeyMode keyMode) {

		StringBuilder buf = new StringBuilder();
		String declaringTypeName = pjp.getSignature().getDeclaringTypeName();
		declaringTypeName = declaringTypeName.substring(declaringTypeName.lastIndexOf(".") + 1,
				declaringTypeName.length());
		buf.append(declaringTypeName).append(".").append(pjp.getSignature().getName());
		if (key.length() > 0) {
			buf.append(".").append(key);
		}

		Object[] args = pjp.getArgs();
		if (keyMode == KeyMode.DEFAULT) {
//        	logger.debug("方法参数个数:" + args.length);
			Annotation[][] pas = ((MethodSignature) pjp.getSignature()).getMethod().getParameterAnnotations();
//            logger.debug("申明cacheKey个数:" + pas.length);
			for (int i = 0; i < pas.length; i++) {
				for (Annotation an : pas[i]) {
					if (an instanceof CacheKey) {
						// 对于基本类型，直接用toString即可，对与对象类型，则需要获取对象序列化值
						Object argObj = args[i];
//                    	String value = SerializeUtil.getValueByType(argObj);
						buf.append(".").append(argObj);
						break;
					}
				}
			}
		} else if (keyMode == KeyMode.BASIC) {
			for (Object argObj : args) {
//            	String value = SerializeUtil.getValueByType(argObj);
				buf.append(".").append(argObj);

			}
		} else if (keyMode == KeyMode.ALL) { // 本系统默认：ALL
			for (Object arg : args) {
//            	String objSerial = SerializeUtil.hessianSerialize(arg);
				buf.append(".").append(arg);
			}
		}
//        logger.debug("最终拼装字符串：" + buf.toString());
		String serialObject = /* Sha1Util.getEncrypteWord( */buf.toString()/* ) */;
//        logger.debug("序列化后的签名：" + serialObject);
		return serialObject;
	}

}