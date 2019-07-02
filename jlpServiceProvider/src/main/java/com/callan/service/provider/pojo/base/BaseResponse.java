package com.callan.service.provider.pojo.base;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BaseResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(name = "Code")
	private String code = "1000";
	@JSONField(name = "Text")
	private String text = "处理成功";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
