package com.gaia.autotrade.ws.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class ResponseMsg {
	private Long ts;
	private Long pong;
	private String id;
	private String status;
	private String subbed;
	private String errcode;
	private String errmsg;
	private String ch;
	private Object tick;

	public Long getTs() {
		return ts;
	}

	@JSONField(name = "ts")
	public void setTs(Long ts) {
		this.ts = ts;
	}

	public String getId() {
		return id;
	}

	@JSONField(name = "id")
	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	@JSONField(name = "status")
	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubbed() {
		return subbed;
	}

	@JSONField(name = "subbed")
	public void setSubbed(String subbed) {
		this.subbed = subbed;
	}

	public String getErrcode() {
		return errcode;
	}

	@JSONField(name = "err-code")
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	@JSONField(name = "err-msg")
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Long getPong() {
		return pong;
	}

	@JSONField(name = "pong")
	public void setPong(Long pong) {
		this.pong = pong;
	}

	public String getCh() {
		return ch;
	}

	@JSONField(name = "ch")
	public void setCh(String ch) {
		this.ch = ch;
	}

	public Object getTick() {
		return tick;
	}

	@JSONField(name = "tick")
	public void setTick(Object tick) {
		this.tick = tick;
	}

}
