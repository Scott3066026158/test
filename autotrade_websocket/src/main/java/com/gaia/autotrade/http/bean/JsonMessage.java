package com.gaia.autotrade.http.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class JsonMessage {
	private String status;
	private String ch;
	private Long ts;
	private Object data;
	private String errCode;
	private String errMsg;
	private String code;
	
	public String getCode() {
		return code;
	}
	
	@JSONField(name = "code")
	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	@JSONField(name = "status")
	public void setStatus(String status) {
		this.status = status;
	}


	public String getCh() {
		return ch;
	}

	@JSONField(name = "ch")
	public void setCh(String ch) {
		this.ch = ch;
	}

	public Long getTs() {
		return ts;
	}

	@JSONField(name = "ts")
	public void setTs(Long ts) {
		this.ts = ts;
	}

	public Object getData() {
		return data;
	}

	@JSONField(name = "data")
	public void setData(Object data) {
		this.data = data;
	}

	public String getErrCode() {
		return errCode;
	}

	@JSONField(name = "err-code")
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	@JSONField(name = "err-msg")
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
