package com.gaia.autotrade.ws.bean;

public class SubKLineData {
	
	// 会话id
	public String m_sid;
	// topic
	public String m_topic;
	// 股票代码
	public String m_code;
	// 股票代码缩写
	public String m_lowCode;
	// 周期
	public int m_cycle;
	// 数据条数
	public int m_size;
	// 复权模式
	public int m_subscription;
	// 类型
	public int m_type;
	// 开始日期
	public double m_startDate;
	// 结束日期
	public double m_endDate;
	
	
	public SubKLineData copy() {
		SubKLineData subKLineData = new SubKLineData();
		subKLineData.m_sid = this.m_sid;
		subKLineData.m_topic = this.m_topic;
		subKLineData.m_code = this.m_code;
		subKLineData.m_lowCode = this.m_lowCode;
		subKLineData.m_cycle = this.m_cycle;
		subKLineData.m_size = this.m_size;
		subKLineData.m_subscription = this.m_subscription;
		subKLineData.m_type = this.m_type;
		subKLineData.m_startDate = this.m_startDate;
		subKLineData.m_endDate = this.m_endDate;
		return subKLineData;
	}
	
	public void copySubData(SubKLineData data) {
		this.m_code = new String(data.m_code);
		this.m_lowCode = new String(data.m_lowCode);
		this.m_cycle = data.m_cycle;
		this.m_size = data.m_size;
		this.m_subscription = data.m_subscription;
		this.m_type = data.m_type;
		this.m_startDate = data.m_startDate;
		this.m_endDate = data.m_endDate;
	}
	
	public String getKey() {
		String key = m_code + m_lowCode + m_cycle + m_subscription + m_type + m_startDate + m_endDate;
		return key;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.hashCode() == this.hashCode();
	}
	
	@Override
    public int hashCode()
    {
		String key = getKey();
		return key.hashCode();
    }
}