package com.gaia.autotrade.ws.bean;

public class SubDataBean {
	// 交易对子
	private String m_pair;
	// 订阅者会话ID
	private String m_sid;
	// 订阅者topic
	private String m_topic;

	public String getSid() {
		return m_sid;
	}

	public void setSid(String sid) {
		this.m_sid = sid;
	}

	public String getTopic() {
		return m_topic;
	}

	public void setTopic(String topic) {
		this.m_topic = topic;
	}
	
	public String getPair() {
		return m_pair;
	}

	public void setPair(String pair) {
		this.m_pair = pair;
	}


}
