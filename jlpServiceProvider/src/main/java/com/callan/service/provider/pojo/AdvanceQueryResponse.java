package com.callan.service.provider.pojo;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.callan.service.provider.pojo.base.BaseResponse;
import com.callan.service.provider.pojo.base.ResponseColumn;

public class AdvanceQueryResponse {
	private BaseResponse response = new BaseResponse();
	private Double totals;
	private List<ResponseColumn> columns;
	private List<Map<String, Object>> content;
	
	public BaseResponse getResponse() {
		return response;
	}
	public void setResponse(BaseResponse response) {
		this.response = response;
	}
	public Double getTotals() {
		return totals;
	}
	public void setTotals(Double totals) {
		this.totals = totals;
	}
	public List<ResponseColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<ResponseColumn> columns) {
		this.columns = columns;
	}
	public List<Map<String, Object>> getContent() {
		return content;
	}
	public void setContent(List<Map<String, Object>> content) {
		this.content = content;
	}
	public String toJsonString() {
		
		return toJSONObject().toJSONString();
	}
	
	 public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("response", response);
        obj.put("totals", totals);
        obj.put("content", content);
        obj.put("columns", columns);
        obj.put("content", content);
        return obj;
    }
}
