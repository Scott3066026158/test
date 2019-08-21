package com.gaia.autotrade.owsock.market_bean;

public class MarketTradeDetailData {
	public String m_pair;
	public String m_lowpair;
	public double m_amount;
	public double m_price;
	public double m_tradetime;
	public String m_diretion;

	public MarketTradeDetailData copy() {
		MarketTradeDetailData data = new MarketTradeDetailData();
		data.m_pair = new String(this.m_pair);
		data.m_lowpair = new String(this.m_lowpair);
		data.m_amount = this.m_amount;
		data.m_price = this.m_price;
		data.m_tradetime = this.m_tradetime;
		data.m_diretion = this.m_diretion;
		return data;
	}

	public void tickToTradeDetailData(MarketTickDetailData data) {
		this.m_amount = data.m_dVolume;
		this.m_diretion = "";
		this.m_lowpair = data.m_lowCode;
		this.m_pair = data.m_code;
		this.m_price = data.m_close;
		this.m_tradetime = data.m_date;
	}
}
