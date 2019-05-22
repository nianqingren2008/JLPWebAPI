package com.callan.service.provider.pojo.base;

public class FieldName {
	private String fieldName;

	public FieldName() {
	}
	
	public FieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public String toString() {
		return fieldName;
	}
	
}
