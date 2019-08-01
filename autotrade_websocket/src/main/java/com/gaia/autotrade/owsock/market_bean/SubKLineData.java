package com.gaia.autotrade.owsock.market_bean;

public class SubKLineData {
	// 股票代码
	public String m_code;
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
		subKLineData.m_code = this.m_code;
		subKLineData.m_cycle = this.m_cycle;
		subKLineData.m_size = this.m_size;
		subKLineData.m_subscription = this.m_subscription;
		subKLineData.m_type = this.m_type;
		subKLineData.m_startDate = this.m_startDate;
		subKLineData.m_endDate = this.m_endDate;
		return subKLineData;
	}
}