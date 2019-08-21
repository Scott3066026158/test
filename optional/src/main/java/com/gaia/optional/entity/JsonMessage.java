package com.gaia.optional.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class JsonMessage {
	private Object data;
	private String code;
	private String msg;

	public Object getData() {
		return data;
	}

	@JSONField(name = "data")
	public void setData(Object data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	@JSONField(name = "code")
	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	@JSONField(name = "msg")
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
