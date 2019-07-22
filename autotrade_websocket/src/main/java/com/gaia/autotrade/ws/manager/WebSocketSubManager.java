package com.gaia.autotrade.ws.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.gaia.autotrade.ws.bean.SubDataBean;

public class WebSocketSubManager {
	//订阅的目标 Depth Tick KLine Other
	//订阅的交易对子 AE/BIC BTC/USDT
	//订阅者 SID
	//订阅推送的规则 即到即发 间隔发送
	
	// Depth数据回调订阅者
	private ConcurrentHashMap<String, Map<String, SubDataBean>> m_depthDataCallBackSubMap = new ConcurrentHashMap<String, Map<String, SubDataBean>>();
	// Tick数据回调订阅者
	private ConcurrentHashMap<String, Map<String, SubDataBean>> m_tickDataCallBackSubMap = new ConcurrentHashMap<String, Map<String, SubDataBean>>();
	
	// Depth数据回调订阅者
	private ConcurrentHashMap<String, Map<String, SubDataBean>> m_depthDataIntervalSubMap = new ConcurrentHashMap<String, Map<String, SubDataBean>>();
	// Tick数据回调订阅者
	private ConcurrentHashMap<String, Map<String, SubDataBean>> m_tickDataIntervalSubMap = new ConcurrentHashMap<String, Map<String, SubDataBean>>();
	
	
	/**
	 * Get指定pair的callback订阅者
	 * @return 订阅者集合
	 */
	/*
	public List<SubDataBean> getDepthCallBackSubList() {
		ArrayList<SubDataBean> result = new ArrayList<SubDataBean>();
		Iterator<Entry<String, List<SubDataBean>>> iter = m_depthDataCallBackSubMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, List<SubDataBean>> entry = (Map.Entry<String, List<SubDataBean>>) iter.next();
			List<SubDataBean> val = entry.getValue();
			result = new ArrayList<SubDataBean>(Arrays.asList(new SubDataBean[val.size()]));
		}
		return result;
	}
	*/
	/*
	//处理重复订阅者
	public boolean putDepthCallBackSubList(SubDataBean subData) {
		List<SubDataBean> list = m_depthDataCallBackSubMap.get(subData.getPair());
		if(list == null){
			list = new ArrayList<SubDataBean>();
		}
		list.add(subData);
		return true;
	}
	*/
}
