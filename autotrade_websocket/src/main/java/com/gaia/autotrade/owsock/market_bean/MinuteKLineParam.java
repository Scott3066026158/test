package com.gaia.autotrade.owsock.market_bean;

public class MinuteKLineParam {
	public int m_cycle;
	public int m_day;
	public int m_hour;
	public int m_hour_cycle;
	public int m_month;
	public int m_minute;
	public int m_minute_cycle;
	public int m_year;

	/**
	 * 比较是否相同
	 * 
	 * @param date 数据
	 * @return 是否相同
	 */
	public final boolean Equal(MinuteKLineParam date) {
		if (date == null) {
			return false;
		}

		if (m_year == date.m_year && m_month == date.m_month && m_day == date.m_day && m_hour_cycle == date.m_hour_cycle
				&& m_minute_cycle == date.m_minute_cycle) {
			return true;
		}
		return false;
	}
}
