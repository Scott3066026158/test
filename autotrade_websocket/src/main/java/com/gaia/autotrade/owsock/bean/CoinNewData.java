package com.gaia.autotrade.owsock.bean;

import java.util.ArrayList;

/**
 股票实时数据
 
*/
public class CoinNewData
{
	//平均价格
	public double m_averagePrice;
	//今虚实度
	public double m_currDelta;
	public int m_dVolume;
	//交易所代码
	public String m_exchangeID = "";
	public int m_innerVol;
	//昨天收盘价格
	public double m_lastClose;
	//跌停价
	public double m_lowerLimit;
	public int m_outerVol;
	//昨收盘
	public double m_preClose;
	//昨虚实度
	public double m_preDelta;
	//昨持仓量
	public double m_preOpenInterest;
	//上次结算价
	public double m_preSettlementPrice;
	//本次结算价
	public double m_settlementPrice;
	//交易日
	public String m_tradingDay = "";
	public double m_turnoverRate;
	//成交金额
	public double m_turnover;
	//最后修改毫秒
	public int m_updateMillisec;
	//最后修改时间
	public String m_updateTime = "";
	//涨停价
	public double m_upperLimit;

	public double m_avgPrice;   //平均价
	public double m_assessment;
	public double m_open;
	public double m_openInterest;
	public double m_prePrice;
	public double m_high;
	public double m_low;
	public double m_close;
	public double m_volume;
	public double m_amount;
	public double m_exchangeRate;
	public int m_askPriceCount;
	public ArrayList<Double> m_askPrices;
	public int m_askVolCount;
	public ArrayList<Double> m_askVols;
	public int m_bidPriceCount;
	public ArrayList<Double> m_bidPrices;

	public int m_bidVolCount;
	public ArrayList<Double> m_bidVols;
	public String m_code;
	public double m_date;
	public double m_thirdPartPrice;
	public int m_type;
	public int m_id;
	public double m_rate;

	public CoinNewData()
	{
		m_askPrices = new ArrayList<>();
		m_askVols = new ArrayList<>();
		m_bidPrices = new ArrayList<>();
		m_bidVols = new ArrayList<>();
		m_assessment = 0;
	}

	/** 
	 复制数据
	 
	 @param data 数据
	*/
	public final void Copy(CoinNewData data)
	{
		if (data == null)
		{
			return;
		}
		m_assessment = data.m_assessment;
		m_avgPrice = data.m_avgPrice;
		m_open = data.m_open;
		m_openInterest = data.m_openInterest;
		m_prePrice = data.m_prePrice;
		m_high = data.m_high;
		m_low = data.m_low;
		m_close = data.m_close;
		m_volume = data.m_volume;
		m_amount = data.m_amount;
		m_askPriceCount = data.m_askPriceCount;
		m_askPrices.clear();
		m_askPrices.addAll(data.m_askPrices);
		m_askVolCount = data.m_askVolCount;
		m_askVols.clear();
		m_askVols.addAll(data.m_askVols);
		m_bidPriceCount = data.m_bidPriceCount;
		m_bidPrices.clear();
		m_bidPrices.addAll(data.m_bidPrices);
		m_bidVolCount = data.m_bidVolCount;
		m_bidVols.clear();
		m_bidVols.addAll(data.m_bidVols);
		m_code = data.m_code;
		m_date = data.m_date;
		m_type = data.m_type;
		m_id = data.m_id;
		m_exchangeRate = data.m_exchangeRate;

		m_averagePrice = data.m_averagePrice;
		m_close = data.m_close;
		m_code = data.m_code;
		m_currDelta = data.m_currDelta;
		m_dVolume = data.m_dVolume;
		m_exchangeID = data.m_exchangeID;
		m_high = data.m_high;
		m_innerVol = data.m_innerVol;
		m_lastClose = data.m_lastClose;
		m_low = data.m_low;
		m_lowerLimit = data.m_lowerLimit;
		m_outerVol = data.m_outerVol;
		m_open = data.m_open;
		m_openInterest = data.m_openInterest;
		m_preClose = data.m_preClose;
		m_preDelta = data.m_preDelta;
		m_preOpenInterest = data.m_preOpenInterest;
		m_preSettlementPrice = data.m_preSettlementPrice;
		m_settlementPrice = data.m_settlementPrice;
		m_tradingDay = data.m_tradingDay;
		m_turnover = data.m_turnover;
		m_turnoverRate = data .m_turnoverRate;
		m_updateMillisec = data.m_updateMillisec;
		m_updateTime = data.m_updateTime;
		m_upperLimit = data.m_upperLimit;
		m_volume = data.m_volume;
		m_date = data.m_date;
		m_rate = data.m_rate;
		m_thirdPartPrice = data.m_thirdPartPrice;
	}

	public boolean VectorEqual(ArrayList<Double> list1, ArrayList<Double>list2)
	{
		int size1 = list1.size();
		int size2 = list2.size();

		if(size1 == size2 && size1 > 0)
		{
			for(int i = 0; i < size1; i++)
			{
				if(list1.get(i) != list2.get(i))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public boolean Equals(Object obj) {
		CoinNewData data = (CoinNewData)obj;
		if (data == null) return false;
		if (m_averagePrice == data.m_averagePrice
				&& m_close == data.m_close
				&& m_currDelta == data.m_currDelta
				&& m_dVolume == data.m_dVolume
				&& m_high == data.m_high
				&& m_low == data.m_low
				&& m_lowerLimit == data.m_lowerLimit
				&& m_open == data.m_open
				&& m_openInterest == data.m_openInterest
				&& m_preClose == data.m_preClose
				&& m_preDelta == data.m_preDelta
				&& m_preOpenInterest == data.m_preOpenInterest
				&& m_preSettlementPrice == data.m_preSettlementPrice
				&& m_settlementPrice == data.m_settlementPrice
				&& m_tradingDay.equals(data.m_tradingDay)
				&& m_turnover == data.m_turnover
				&& m_turnoverRate == data.m_turnoverRate
				&& m_updateMillisec == data.m_updateMillisec
				&& m_updateTime.equals(data.m_updateTime)
				&& m_upperLimit == data.m_upperLimit
				&& m_volume == data.m_volume
				&& m_askPriceCount == data.m_askPriceCount
				&& m_askVolCount == data.m_askVolCount
				&& m_bidPriceCount == data.m_bidPriceCount
				&& m_bidVolCount == data.m_bidVolCount
				&& m_assessment == data.m_assessment
				&& VectorEqual(m_askPrices, data.m_askPrices)
				&& VectorEqual(m_askVols, data.m_askVols)
				&& VectorEqual(m_bidPrices, data.m_bidPrices)
				&& VectorEqual(m_bidVols, data.m_bidVols)
				)
		{
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "CoinNewData [m_averagePrice=" + m_averagePrice + ", m_currDelta=" + m_currDelta + ", m_dVolume="
				+ m_dVolume + ", m_exchangeID=" + m_exchangeID + ", m_innerVol=" + m_innerVol + ", m_lastClose="
				+ m_lastClose + ", m_lowerLimit=" + m_lowerLimit + ", m_outerVol=" + m_outerVol + ", m_preClose="
				+ m_preClose + ", m_preDelta=" + m_preDelta + ", m_preOpenInterest=" + m_preOpenInterest
				+ ", m_preSettlementPrice=" + m_preSettlementPrice + ", m_settlementPrice=" + m_settlementPrice
				+ ", m_tradingDay=" + m_tradingDay + ", m_turnoverRate=" + m_turnoverRate + ", m_turnover=" + m_turnover
				+ ", m_updateMillisec=" + m_updateMillisec + ", m_updateTime=" + m_updateTime + ", m_upperLimit="
				+ m_upperLimit + ", m_avgPrice=" + m_avgPrice + ", m_assessment=" + m_assessment + ", m_open=" + m_open
				+ ", m_openInterest=" + m_openInterest + ", m_prePrice=" + m_prePrice + ", m_high=" + m_high
				+ ", m_low=" + m_low + ", m_close=" + m_close + ", m_volume=" + m_volume + ", m_amount=" + m_amount
				+ ", m_exchangeRate=" + m_exchangeRate + ", m_askPriceCount=" + m_askPriceCount + ", m_askPrices="
				+ m_askPrices + ", m_askVolCount=" + m_askVolCount + ", m_askVols=" + m_askVols + ", m_bidPriceCount="
				+ m_bidPriceCount + ", m_bidPrices=" + m_bidPrices + ", m_bidVolCount=" + m_bidVolCount + ", m_bidVols="
				+ m_bidVols + ", m_code=" + m_code + ", m_date=" + m_date + ", m_thirdPartPrice=" + m_thirdPartPrice
				+ ", m_type=" + m_type + ", m_id=" + m_id + ", m_rate=" + m_rate + "]";
	}
}