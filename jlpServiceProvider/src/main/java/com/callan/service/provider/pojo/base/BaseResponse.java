package com.callan.service.provider.pojo.base;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class BaseResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JSONField(label = "Code")
	private String Code="10000";
	
	@JSONField(label = "Text")
	private String Text="OK";
	
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
	
}
