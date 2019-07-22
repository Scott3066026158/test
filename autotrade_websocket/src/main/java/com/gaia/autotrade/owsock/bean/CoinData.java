package com.gaia.autotrade.owsock.bean;

public class CoinData {
	
	public CoinData()
	{
	}
	
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
	
	public void Copy(CoinData data)
	{
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
}
