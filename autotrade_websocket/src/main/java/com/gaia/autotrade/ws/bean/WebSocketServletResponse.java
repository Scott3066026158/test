package com.gaia.autotrade.ws.bean;

public class WebSocketServletResponse {
	// 返回请求参数
	private String m_requestParms;
	// 返回结果码
	private String m_status;
	// 返回结果码描述信息
	private String m_msg;
	// 返回请求的自定义ID
	private String m_paramID;
	// 返回服务器时间
	private long m_timestamp;
	// 返回目标的会话ID
	private String m_sid;
	// 返回结果数据
	private Object m_data;

	public String getRequestParms() {
		return m_requestParms;
	}

	public void setRequestParms(String requestParms) {
		this.m_requestParms = requestParms;
	}

	public String getStatus() {
		return m_status;
	}

	public void setStatus(String status) {
		this.m_status = status;
	}

	public String getMsg() {
		return m_msg;
	}

	public void setMsg(String msg) {
		this.m_msg = msg;
	}

	public String getParamID() {
		return m_paramID;
	}

	public void setParamID(String paramID) {
		this.m_paramID = paramID;
	}

	public long getTimestamp() {
		return m_timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.m_timestamp = timestamp;
	}

	public String getSid() {
		return m_sid;
	}

	public void setSid(String sid) {
		this.m_sid = sid;
	}

	public Object getData() {
		return m_data;
	}

	public void setData(Object data) {
		this.m_data = data;
	}

}
