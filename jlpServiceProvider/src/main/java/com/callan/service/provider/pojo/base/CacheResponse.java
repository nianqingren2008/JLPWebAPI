package com.callan.service.provider.pojo.base;

/**
 * 使用缓存的返回数据格式
 * @author callan
 *
 */
public class CacheResponse {
	/*
	 * 成功标识  0 ：成功
	 */
	private int code = -1;
	/*
	 * 返回业务数据
	 */
	private Object data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
