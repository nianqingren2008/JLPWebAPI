package com.callan.service.provider.pojo.cache;

/**
 * 加解密的处理类
 * @author lixx
 *
 */
public class Sha1Util {
	public static final String SALT_KEY = "83ffeb614b8cdf7832915440ab16f72ce54d7b73";
	
	/**
	 * 获取加密后的密文
	 * @param password
	 * @return
	 */
	public static String getEncrypteWord(String password){
		Sha1 sha1 = new Sha1();
		String temp = sha1.getDigestOfString(password.getBytes());
        String newPwd = sha1.getDigestOfString((SALT_KEY + temp).getBytes());
        
        return newPwd;
	}
}
