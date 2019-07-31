package com.gaia.autotrade.owsock.market_bean;

public class MarketTickDetailData {
	// 24小时成交额
	public double m_tradeAmountIn24Hour;
	// 24小时成交量
	public double m_tradeVolIn24Hour;
	// 24小时最高价
	public double m_topPriceIn24Hour;
	// 24小时最低价
	public double m_floorPriceIn24Hour;

	// 交易所名称
	public String m_exchName;
	// 交易对子
	public String m_code;
	// 交易对子缩写
	public String m_lowCode;
	// 类型 116标识成交数据 117标识深度数据
	public int m_type;
	// 最新价格
	public double m_close;
	// 昨日收盘价格
	public double m_preClose;
	// 每笔行情的成交差
	public double m_dVolume;
	// 日期
	public double m_date;
	// 交易日 交易日与修改时间由 m_date分化
	public String m_tradeDay;
	// 修改时间
	public String m_tradeTime;

	public MarketTickDetailData() {

	}

	// 提取Tick数据中有效数据
	public MarketTickDetailData coinNewDataToMarketTickData(CoinNewData coinNewData) {

		this.m_tradeAmountIn24Hour = coinNewData.m_turnover;
		this.m_tradeVolIn24Hour = coinNewData.m_volume;
		this.m_topPriceIn24Hour = coinNewData.m_high;
		this.m_floorPriceIn24Hour = coinNewData.m_low;

		this.m_exchName = coinNewData.m_exchangeID;
		this.m_code = coinNewData.m_code;
		this.m_lowCode = coinNewData.m_code.replace("/", "").toLowerCase();
		this.m_type = coinNewData.m_type;
		this.m_close = coinNewData.m_close;
		this.m_preClose = coinNewData.m_preClose;
		this.m_dVolume = coinNewData.m_dVolume;
		this.m_date = coinNewData.m_date;
		this.m_tradeDay = coinNewData.m_tradingDay;
		this.m_tradeTime = coinNewData.m_updateTime;
		return this;
	}

	public MarketTickDetailData copy(MarketTickDetailData data) {
		this.m_tradeAmountIn24Hour = data.m_tradeAmountIn24Hour;
		this.m_tradeVolIn24Hour = data.m_tradeVolIn24Hour;
		this.m_topPriceIn24Hour = data.m_topPriceIn24Hour;
		this.m_floorPriceIn24Hour = data.m_floorPriceIn24Hour;

		this.m_exchName = new String(data.m_exchName);
		this.m_code = new String(data.m_code);
		this.m_lowCode = new String(data.m_lowCode);
		this.m_type = data.m_type;
		this.m_close = data.m_close;
		this.m_preClose = data.m_preClose;
		this.m_dVolume = data.m_dVolume;
		this.m_date = data.m_date;
		this.m_tradeDay = new String(data.m_tradeDay);
		this.m_tradeTime = new String(data.m_tradeTime);
		return this;
	}

	public MarketTickDetailData copy() {
		MarketTickDetailData data = new MarketTickDetailData();
		data.m_tradeAmountIn24Hour = this.m_tradeAmountIn24Hour;
		data.m_tradeVolIn24Hour = this.m_tradeVolIn24Hour;
		data.m_topPriceIn24Hour = this.m_topPriceIn24Hour;
		data.m_floorPriceIn24Hour = this.m_floorPriceIn24Hour;

		data.m_exchName = new String(this.m_exchName);
		data.m_code = new String(this.m_code);
		data.m_lowCode = new String(this.m_lowCode);
		data.m_type = this.m_type;
		data.m_close = this.m_close;
		data.m_preClose = this.m_preClose;
		data.m_dVolume = this.m_dVolume;
		data.m_date = this.m_date;
		data.m_tradeDay = new String(this.m_tradeDay);
		data.m_tradeTime = new String(this.m_tradeTime);
		return this;
	}
}
