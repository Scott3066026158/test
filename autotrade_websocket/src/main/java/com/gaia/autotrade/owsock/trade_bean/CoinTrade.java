package com.gaia.autotrade.owsock.trade_bean;

public class CoinTrade {

	public CoinTrade() {
	}

	public String m_code;// 合约代码
	public String m_dealID;
	public String m_orderID;
	public String m_traderID;
	public double m_volume; // 数量
	public double m_price;// 价格
	public double m_timestamp;
	public int m_direction; // 买卖方向
	public int m_isFinish;

	/**
	 * 复制数据
	 * 
	 * @param data 数据
	 */
	public final void Copy(CoinTrade data) {
		if (data == null) {
			return;
		}
		m_code = data.m_code;
		m_dealID = data.m_dealID;
		m_orderID = data.m_orderID;
		m_traderID = data.m_traderID;
		m_volume = data.m_volume;
		m_price = data.m_price;
		m_timestamp = data.m_timestamp;
		m_direction = data.m_direction;
		m_isFinish = data.m_isFinish;
	}

	/**
	 * 比较是否相同
	 * 
	 * @param data 数据
	 * @return 是否相同
	 */
	public final boolean Equal(CoinTrade data) {
		if (data == null) {
			return false;
		}
		return false;
	}
}
