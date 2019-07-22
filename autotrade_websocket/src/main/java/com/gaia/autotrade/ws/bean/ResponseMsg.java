package com.gaia.autotrade.ws.bean;

public class ResponseMsg implements ResponseMsgInterfact {
	private String id;
	private String status;
	private String reqmsg;
	private long ts;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReqmsg() {
		return reqmsg;
	}

	public void setReqmsg(String reqmsg) {
		this.reqmsg = reqmsg;
	}

	public long getTs() {
		return ts;
	}

	public void setTs(long ts) {
		this.ts = ts;
	}

}
