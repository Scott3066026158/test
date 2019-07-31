package com.gaia.autotrade.owsock.trade_bean;

public class DepositAddress {
	public String m_flag;
	public String m_traderID;
	public int m_errorCode;
	public String m_code;
	public String m_addr;
	public String m_memo;

	public DepositAddress() {
	}

	public void Copy(DepositAddress copy) {
		m_addr = copy.m_addr;
		m_code = copy.m_code;
		m_flag = copy.m_flag;
		m_traderID = copy.m_traderID;
		m_memo = copy.m_memo;
		m_errorCode = copy.m_errorCode;
	}
}