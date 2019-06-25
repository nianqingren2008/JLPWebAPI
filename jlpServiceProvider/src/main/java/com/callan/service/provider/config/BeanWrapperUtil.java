package com.callan.service.provider.config;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对象复制类
 */
public class BeanWrapperUtil {
	
	private static Logger log = LoggerFactory.getLogger(BeanWrapperUtil.class);

	/**
	 * 属性复制
	 * @param source 源Bean对象
	 * @param target 目标Bean对象
	 * @param nullValueCopy 是否复制null值
	 */
	public static void copyProperties(Object source, Object target, boolean nullValueCopy) {
		Method[] methods = source.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("get")) {
				try {
					Object value = method.invoke(source, new Object[0]);
					// 如果value为null, 不允许null值复制,则不进行复制
					if (value == null && !nullValueCopy) {
						continue;
					}
					String setMethodName = method.getName().replaceFirst("get", "set");
					Method setMethod = target.getClass().getMethod(setMethodName, method.getReturnType());
					setMethod.invoke(target, value);
				} catch (Exception e) {
					// log.error("copy properties error " +
					// method.getName().substring(3), e);
				}
			}
		}
	}

	/**
	 * 属性复制
	 * @param source 源Bean对象
	 * @param target 目标Bean对象
	 */
	public static void copyProperties(Object source, Object target) {
		copyProperties(source, target, true);
	}

	/**
	 * 获取Bean对象属性值
	 * @param beanObj Bean对象
	 * @param property 属性名称
	 * @return 属性值
	 */
	public static Object getPropertyValue(Object beanObj, String property) {
		try {
			int index = property.indexOf('.');
			if (index > -1) {
				Object subObj = getPropertyValue(beanObj, property.substring(0, index));
				return subObj == null ? null : getPropertyValue(subObj, property.substring(index + 1));
			}
			String methodName = "get" + Character.toUpperCase(property.charAt(0)) + property.substring(1);
			Method method = beanObj.getClass().getMethod(methodName, new Class[0]);
			Object value = method.invoke(beanObj, new Object[0]);
			return value;
		} catch (Exception e) {
			log.error("get properties error " + property, e);
		}
		return null;
	}

	/**
	 * 设置属性对象值
	 * @param beanObj Bean对象
	 * @param property 属性名称
	 * @param value 属性值
	 */
	public static void setPropertyValue(Object beanObj, String property, Object value) {
		try {
			int index = property.lastIndexOf('.');
			if (index > -1) {
				beanObj = getPropertyValue(beanObj, property.substring(0, index));
				if (beanObj == null) {
					return;
				}
				property = property.substring(index + 1);
			}
			String methodName = "set" + Character.toUpperCase(property.charAt(0)) + property.substring(1);
			Method setMethod = null;
			Method[] methods = beanObj.getClass().getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					setMethod = method;
					break;
				}
			}
			if (setMethod != null) {
				setMethod.invoke(beanObj, value);
			}
		} catch (Exception e) {
			log.error("get properties error " + property, e);
		}
	}
}
