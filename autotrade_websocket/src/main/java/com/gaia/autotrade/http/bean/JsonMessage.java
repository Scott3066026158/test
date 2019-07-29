package com.gaia.autotrade.http.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class JsonMessage {
	@JSONField(name = "status")
	private String status;
	@JSONField(name = "ch")
	private String ch;
	@JSONField(name = "ts")
	private Long ts;
	@JSONField(name = "data")
	private Object data;
	@JSONField(name = "err-code")
	private String errCode;
	@JSONField(name = "err-msg")
	private String errMsg;

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getCh() {
		return ch;
	}

	public void setCh(String ch) {
		this.ch = ch;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
