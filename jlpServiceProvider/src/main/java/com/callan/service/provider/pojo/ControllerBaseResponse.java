package com.callan.service.provider.pojo;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.pojo.base.BaseResponse;

public class ControllerBaseResponse {
	private BaseResponse response = new BaseResponse();

	public BaseResponse getResponse() {
		return response;
	}

	public void setResponse(BaseResponse response) {
		this.response = response;
	}

	public String toJsonString() {
		return toJSONObject().toJSONString();
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put("response", response);
		return obj;
	}
}
