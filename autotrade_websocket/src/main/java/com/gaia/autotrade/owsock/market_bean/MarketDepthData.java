package com.gaia.autotrade.owsock.market_bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MarketDepthData {
	// 交易对子
	public String m_code;
	// 交易对子缩写
	public String m_lowCode;
	// 类型 116标识成交数据 117标识深度数据
	public int m_type;
	// 日期
	public double m_date;
	// 交易所名称
	public String m_exchName;

	// 买档价格深度数量
	public int m_bidPriceCount;
	// 买档数量深度数量 原则上价格与数量深度一致
	public int m_bidVolumeCount;
	// 买档价格深度
	public List<Double> m_bidPriceList;
	// 买档数量深度
	public List<Double> m_bidVolumeList;

	// 卖档价格深度数量
	public int m_askPriceCount;
	// 卖档数量深度数量 原则上价格与数量深度一致
	public int m_askVolumeCount;
	// 卖档价格深度
	public List<Double> m_askPriceList;
	// 卖档数量深度
	public List<Double> m_askVolumeList;

	// 提取Depth数据中有效数据
	public MarketDepthData coinNewDataToMarketDepthData(CoinNewData coinNewData) {
		this.m_code = coinNewData.m_code;
		this.m_lowCode = coinNewData.m_code.replace("/", "").toLowerCase();
		this.m_type = coinNewData.m_type;
		this.m_date = coinNewData.m_date;
		this.m_exchName = coinNewData.m_exchangeID;

		this.m_bidPriceCount = coinNewData.m_bidPriceCount;
		this.m_bidVolumeCount = coinNewData.m_bidVolCount;
		this.m_bidPriceList = coinNewData.m_bidPrices;
		this.m_bidVolumeList = coinNewData.m_bidVols;

		this.m_askPriceCount = coinNewData.m_bidPriceCount;
		this.m_askVolumeCount = coinNewData.m_bidPriceCount;
		this.m_askPriceList = coinNewData.m_askPrices;
		this.m_askVolumeList = coinNewData.m_askVols;
		return this;
	}

	public MarketDepthData copy(MarketDepthData data) {
		this.m_code = new String(data.m_code);
		this.m_lowCode = new String(data.m_lowCode);
		this.m_type = data.m_type;
		this.m_date = data.m_date;
		this.m_exchName = new String(data.m_exchName);

		this.m_bidPriceCount = data.m_bidPriceCount;
		this.m_bidVolumeCount = data.m_bidVolumeCount;
		this.m_bidPriceList = new ArrayList<Double>(Arrays.asList(new Double[data.m_bidPriceList.size()]));
		this.m_bidVolumeList = new ArrayList<Double>(Arrays.asList(new Double[data.m_bidVolumeList.size()]));

		this.m_askPriceCount = data.m_bidPriceCount;
		this.m_askVolumeCount = data.m_askVolumeCount;
		this.m_askPriceList = new ArrayList<Double>(Arrays.asList(new Double[data.m_askPriceList.size()]));
		this.m_askVolumeList = new ArrayList<Double>(Arrays.asList(new Double[data.m_askVolumeList.size()]));

		return this;
	}

	public MarketDepthData copy() {
		MarketDepthData data = new MarketDepthData();
		data.m_code = new String(this.m_code);
		data.m_lowCode = new String(this.m_lowCode);
		data.m_type = this.m_type;
		data.m_date = this.m_date;
		data.m_exchName = new String(this.m_exchName);

		data.m_bidPriceCount = this.m_bidPriceCount;
		data.m_bidVolumeCount = this.m_bidVolumeCount;
		data.m_bidPriceList = new ArrayList<Double>(Arrays.asList(new Double[this.m_bidPriceList.size()]));
		Collections.copy(data.m_bidPriceList, this.m_bidPriceList);
		data.m_bidVolumeList = new ArrayList<Double>(Arrays.asList(new Double[this.m_bidVolumeList.size()]));
		Collections.copy(data.m_bidVolumeList, this.m_bidVolumeList);
		data.m_askPriceCount = this.m_bidPriceCount;
		data.m_askVolumeCount = this.m_askVolumeCount;
		data.m_askPriceList = new ArrayList<Double>(Arrays.asList(new Double[this.m_askPriceList.size()]));
		Collections.copy(data.m_askPriceList, this.m_askPriceList);
		data.m_askVolumeList = new ArrayList<Double>(Arrays.asList(new Double[this.m_askVolumeList.size()]));
		Collections.copy(data.m_askVolumeList, this.m_askVolumeList);
		return data;
	}

}
