package com.gaia.autotrade.ws.bean;

import java.util.Map;

public class WebSocketServletRequest {
	// 请求的服务名
	private String m_serviceName;
	// 请求的主题
	private String m_topic;
	// 请求的用户自生成ID
	private String m_paramID;
	// 请求发起的会话ID
	private String m_sid;
	// 请求的主题参数
	private Map<String, Object> m_params;

	public String getServiceName() {
		return m_serviceName;
	}

	public void setServiceName(String serviceName) {
		this.m_serviceName = serviceName;
	}

	public String getTopic() {
		return m_topic;
	}

	public void setTopic(String topic) {
		this.m_topic = topic;
	}

	public String getParamID() {
		return m_paramID;
	}

	public void setParamID(String paramID) {
		this.m_paramID = paramID;
	}

	public String getSid() {
		return m_sid;
	}

	public void setSid(String sid) {
		this.m_sid = sid;
	}

	public Map<String, Object> getParams() {
		return m_params;
	}

	public void setParams(Map<String, Object> params) {
		this.m_params = params;
	}
}
