package com.callan.service.provider.pojo.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

public class SerializeUtil {
	private static Log logger = LogFactory.getLog(SerializeUtil.class);
	
    /**
     * 序列化
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
    
    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
    
    /**
     * 序列化
     * @param object
     * @return
     */
    public static String hessianSerialize(Object object) {
        try {
        	ByteArrayOutputStream bos = new ByteArrayOutputStream();
        	HessianOutput ho = new HessianOutput(bos);
        	ho.writeObject(object);
        	String outStr = bos.toString("ISO-8859-1");
            return outStr;
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
    
    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object hessianUnserialize(String str) {
        try {
            // 反序列化
        	ByteArrayInputStream bis = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
			HessianInput hi = new HessianInput(bis);
			Object redObject = hi.readObject();
            return redObject;
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
    
    
    public static String getValueByType(Object argObj) {
		String value = null;
		if(argObj instanceof String) {
			logger.debug("对象类型为String");
			value = argObj.toString();
        } else if(argObj instanceof Integer || argObj instanceof Long || argObj instanceof Short) {
        	logger.debug("对象类型为Integer");
        	value = argObj.toString();
        } else if(argObj instanceof Boolean) {
        	logger.debug("对象类型为Boolean");
        	value = argObj.toString();
        } else if(argObj instanceof Date) {
        	logger.debug("对象类型为Date");
        	value = argObj.toString();
        } else if(argObj instanceof Double) {
        	logger.debug("对象类型为Double");
        	value = argObj.toString();
        } else if(argObj instanceof Float) {
        	logger.debug("对象类型为Float");
        	value = argObj.toString();
        }else{
        	String objSerial = SerializeUtil.hessianSerialize(argObj);
        	logger.debug("获取对象序列化值:" + objSerial);
        	value = objSerial;
        }
		//预防StringBuffer添加null
		if(value == null){
			value = "";
		}
		return value;
	}
    
}