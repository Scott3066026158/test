package com.gaia.autotrade.owsock.market_bean;

/**
 * 股票实时数据
 * 
 */
public class SecurityLatestDataTiny {
	public double m_assessment;
	public double m_close;
	public String m_code;
	public double m_preclose;
	public double m_rate;
	public double m_vol;

	public SecurityLatestDataTiny() {
		m_assessment = 0;
		m_close = 0;
		m_code = "";
		m_preclose = 0;
		m_vol = 0;
	}

	/**
	 * 复制数据
	 * 
	 * @param tinyData 数据
	 */
	public final void Copy(SecurityLatestDataTiny tinyData) {
		m_assessment = tinyData.m_assessment;
		m_close = tinyData.m_close;
		m_code = tinyData.m_code;
		m_preclose = tinyData.m_preclose;
		m_vol = tinyData.m_vol;
	}

}