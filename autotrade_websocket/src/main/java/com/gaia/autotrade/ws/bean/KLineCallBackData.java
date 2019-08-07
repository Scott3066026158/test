package com.gaia.autotrade.ws.bean;

import java.util.ArrayList;

import com.gaia.autotrade.owsock.market_bean.MarketKLineData;

public class KLineCallBackData {
	//订阅数据
	private SubKLineData subData = new SubKLineData();
	//数据
	private ArrayList<MarketKLineData> datas = new ArrayList<MarketKLineData>();
	
	public SubKLineData getSubData() {
		return subData;
	}
	public void setSubData(SubKLineData subData) {
		this.subData = subData;
	}
	public ArrayList<MarketKLineData> getDatas() {
		return datas;
	}
	public void setDatas(ArrayList<MarketKLineData> datas) {
		this.datas = datas;
	}
	
	public String getKey() {
		return subData.getKey();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.hashCode() == this.hashCode();
	}
	
	@Override
    public int hashCode()
    {
		return subData.hashCode();
    }
	
	public KLineCallBackData copy() {
		KLineCallBackData data = new KLineCallBackData();
		data.setSubData(subData.copy());
		for(MarketKLineData klineData : datas) {
			data.getDatas().add(klineData.copy());
		}
		return data;
	}
	
}
