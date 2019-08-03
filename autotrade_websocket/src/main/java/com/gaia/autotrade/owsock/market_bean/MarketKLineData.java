package com.gaia.autotrade.owsock.market_bean;

import com.gaia.autotrade.ws.bean.SubKLineData;

public class MarketKLineData {

	public MarketKLineData() {
	}

	public SubKLineData m_subKLineData;
	public double m_avgPrice;
	public double m_date;
	public double m_open;
	public double m_openInterest;
	public double m_settlePrice;
	public double m_high;
	public double m_low;
	public double m_close;
	public double m_volume;
	public double m_amount;

	public void copy(MarketKLineData data) {
		m_subKLineData = data.m_subKLineData;
		m_avgPrice = data.m_avgPrice;
		m_date = data.m_date;
		m_open = data.m_open;
		m_openInterest = data.m_openInterest;
		m_settlePrice = data.m_settlePrice;
		m_high = data.m_high;
		m_low = data.m_low;
		m_close = data.m_close;
		m_volume = data.m_volume;
		m_amount = data.m_amount;
	}

	public MarketKLineData copy() {
		MarketKLineData coinData = new MarketKLineData();
		coinData.m_subKLineData = this.m_subKLineData;
		coinData.m_avgPrice = this.m_avgPrice;
		coinData.m_date = this.m_date;
		coinData.m_open = this.m_open;
		coinData.m_openInterest = this.m_openInterest;
		coinData.m_settlePrice = this.m_settlePrice;
		coinData.m_high = this.m_high;
		coinData.m_low = this.m_low;
		coinData.m_close = this.m_close;
		coinData.m_volume = this.m_volume;
		coinData.m_amount = this.m_amount;
		return coinData;
	}
}
