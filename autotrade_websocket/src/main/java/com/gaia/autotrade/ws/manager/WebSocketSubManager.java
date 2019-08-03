package com.gaia.autotrade.ws.manager;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaia.autotrade.owsock.manager.MarketDataManager;
import com.gaia.autotrade.owsock.market_bean.MarketDepthData;
import com.gaia.autotrade.owsock.market_bean.MarketTickDetailData;
import com.gaia.autotrade.owsock.market_bean.MarketTradeDetailData;
import com.gaia.autotrade.ws.bean.KLinePushTick;
import com.gaia.autotrade.ws.bean.SubDataBean;
import com.gaia.autotrade.ws.bean.SubKLineData;

@Component
public class WebSocketSubManager {
	// 订阅的目标 Depth Tick KLine Other
	// 订阅的交易对子 AE/BIC BTC/USDT
	// 订阅者 SID
	// 订阅推送的规则 即到即发 间隔发送

	public WebSocketSubManager() {
		// 设置订阅者中的行情引用
		m_marketDataManager.setWebSocketSubManager(this);
	}
	
	// Depth数据订阅集合 (即到即发)
	private ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>> m_depthDataCallBackSubMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>>();
	// Tick数据订阅集合 (即到即发)
	private ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>> m_tickDataCallBackSubMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>>();
	// Trade数据订阅集合 (即到即发)
	private ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>> m_tradeDataCallBackSubMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>>();
	// KLine数据订阅者集合(即到即发)
	private ConcurrentHashMap<Integer, ConcurrentHashMap<String, SubKLineData>> m_klineDataCallBackSubMap = new ConcurrentHashMap<Integer, ConcurrentHashMap<String, SubKLineData>>();
	// KLine数据请求者集合(即到即发)
	private ConcurrentHashMap<Integer, SubKLineData> m_klineDataCallBackReqMap = new ConcurrentHashMap<Integer, SubKLineData>();
	// Depth数据回调订阅者 (间隔发送)
	private ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>> m_depthDataIntervalSubMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>>();
	// Tick数据回调订阅者 (间隔发送)
	private ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>> m_tickDataIntervalSubMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>>();
	// KLine数据回调订阅者 (间隔发送)
	private ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>> m_klineDataIntervalSubMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>>();

	// 行情数据推送器
	private WebSocketMarketDataPusher m_marketDataPusher;
	// 行情数据管理器
	private MarketDataManager m_marketDataManager = MarketDataManager.getInstance();

	@Autowired
	public void setWebSocketMarketDataPusher(WebSocketMarketDataPusher marketDataPusher) {
		m_marketDataPusher = marketDataPusher;
	}

	// 获取深度订阅者集合(深克隆)
	public Map<String, SubDataBean> getAllCallBackDepth(String pair) {
		if (m_depthDataCallBackSubMap.containsKey(pair)) {
			ConcurrentHashMap<String, SubDataBean> result = new ConcurrentHashMap<String, SubDataBean>();
			ConcurrentHashMap<String, SubDataBean> queue = m_depthDataCallBackSubMap.get(pair);
			Iterator<Map.Entry<String, SubDataBean>> iter = queue.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, SubDataBean> entry = iter.next();
				SubDataBean newBean = entry.getValue().copy();
				result.put(newBean.getSid(), newBean);
			}
			return result;
		} else {
			return null;
		}
	}

	// 获取深度订阅者集合(浅克隆)
	public Map<String, SubDataBean> getAllCallBackDepthByNoCopy(String pair) {
		if (m_depthDataCallBackSubMap.containsKey(pair)) {
			ConcurrentHashMap<String, SubDataBean> result = new ConcurrentHashMap<String, SubDataBean>();
			ConcurrentHashMap<String, SubDataBean> queue = m_depthDataCallBackSubMap.get(pair);
			result.putAll(queue);
			return result;
		} else {
			return null;
		}
	}

	// 获取深度订阅者
	public SubDataBean getCallBackDepth(String pair, String sid) {
		if (m_depthDataCallBackSubMap.containsKey(pair)) {
			SubDataBean bean = m_depthDataCallBackSubMap.get(pair).get(sid);
			if (bean == null) {
				return null;
			} else {
				return bean.copy();
			}
		} else {
			return null;
		}
	}

	// 添加深度订阅
	public boolean putCallBackDepth(SubDataBean bean) {
		if (m_depthDataCallBackSubMap.containsKey(bean.getPair())) {
			ConcurrentHashMap<String, SubDataBean> queue = m_depthDataCallBackSubMap.get(bean.getPair());
			queue.put(bean.getSid(), bean);
		} else {
			ConcurrentHashMap<String, SubDataBean> map = new ConcurrentHashMap<String, SubDataBean>();
			map.put(bean.getSid(), bean);
			m_depthDataCallBackSubMap.put(bean.getPair(), map);
		}

		// 添加订阅的同时推送一笔最新数据
		MarketDepthData data = m_marketDataManager.getDepthData(bean.getPair());
		m_marketDataPusher.addDepthPushPair(data);
		return true;
	}

	// 删除深度订阅
	public boolean removeCallBackDepth(String pair, String sid) {
		if (m_depthDataCallBackSubMap.containsKey(pair)) {
			ConcurrentHashMap<String, SubDataBean> queue = m_depthDataCallBackSubMap.get(pair);
			queue.remove(sid);
		}
		return true;
	}

	// 根据sid删除深度订阅者
	public boolean removeCallBackDepth(String sid) {
		Iterator<Entry<String, ConcurrentHashMap<String, SubDataBean>>> iter = m_depthDataCallBackSubMap.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Entry<String, ConcurrentHashMap<String, SubDataBean>> entry = iter.next();
			ConcurrentHashMap<String, SubDataBean> subMap = entry.getValue();
			subMap.remove(sid);
		}
		return true;
	}

	// 获取Tick订阅者集合
	public Map<String, SubDataBean> getAllCallBackTick(String pair) {
		if (m_tickDataCallBackSubMap.containsKey(pair)) {
			ConcurrentHashMap<String, SubDataBean> result = new ConcurrentHashMap<String, SubDataBean>();
			ConcurrentHashMap<String, SubDataBean> queue = m_tickDataCallBackSubMap.get(pair);
			Iterator<Map.Entry<String, SubDataBean>> iter = queue.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, SubDataBean> entry = iter.next();
				SubDataBean newBean = entry.getValue().copy();
				result.put(newBean.getSid(), newBean);
			}
			return result;
		} else {
			return null;
		}
	}

	// 获取Tick订阅者集合(浅克隆)
	public Map<String, SubDataBean> getAllCallBackTickByNoCopy(String pair) {
		if (m_tickDataCallBackSubMap.containsKey(pair)) {
			ConcurrentHashMap<String, SubDataBean> result = new ConcurrentHashMap<String, SubDataBean>();
			ConcurrentHashMap<String, SubDataBean> queue = m_tickDataCallBackSubMap.get(pair);
			result.putAll(queue);
			return result;
		} else {
			return null;
		}
	}

	// 获取Tick订阅者
	public SubDataBean getCallBackTick(String pair, String sid) {
		if (m_tickDataCallBackSubMap.containsKey(pair)) {
			SubDataBean bean = m_tickDataCallBackSubMap.get(pair).get(sid);
			if (bean == null) {
				return null;
			} else {
				return bean.copy();
			}
		} else {
			return null;
		}
	}

	// 添加Tick订阅
	public boolean putCallBackTick(SubDataBean bean) {
		if (m_tickDataCallBackSubMap.containsKey(bean.getPair())) {
			ConcurrentHashMap<String, SubDataBean> queue = m_tickDataCallBackSubMap.get(bean.getPair());
			queue.put(bean.getSid(), bean);
		} else {
			ConcurrentHashMap<String, SubDataBean> map = new ConcurrentHashMap<String, SubDataBean>();
			map.put(bean.getSid(), bean);
			m_tickDataCallBackSubMap.put(bean.getPair(), map);
		}
		// 添加订阅的同时推送一笔最新数据
		MarketTickDetailData data = m_marketDataManager.getTickData(bean.getPair());
		m_marketDataPusher.addTickPushPair(data);
		return true;
	}

	// 删除Tick订阅
	public boolean removeCallBackTick(String pair, String sid) {
		if (m_tickDataCallBackSubMap.containsKey(pair)) {
			ConcurrentHashMap<String, SubDataBean> queue = m_tickDataCallBackSubMap.get(pair);
			queue.remove(sid);
		}
		return true;
	}
	
	// 删除Tick订阅
	public boolean removeCallBackTick(String sid) {
		Iterator<Entry<String, ConcurrentHashMap<String, SubDataBean>>> iter = m_tickDataCallBackSubMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, ConcurrentHashMap<String, SubDataBean>> entry = iter.next();
			ConcurrentHashMap<String, SubDataBean> subMap = entry.getValue();
			subMap.remove(sid);
		}
		return true;
	}
	
	// 添加Trade订阅
	public boolean putCallBackTrade(SubDataBean bean) {
		if (m_tradeDataCallBackSubMap.containsKey(bean.getPair())) {
			ConcurrentHashMap<String, SubDataBean> queue = m_tradeDataCallBackSubMap.get(bean.getPair());
			queue.put(bean.getSid(), bean);
		} else {
			ConcurrentHashMap<String, SubDataBean> map = new ConcurrentHashMap<String, SubDataBean>();
			map.put(bean.getSid(), bean);
			m_tradeDataCallBackSubMap.put(bean.getPair(), map);
		}
		// 添加订阅的同时推送一笔最新数据
		MarketTradeDetailData data = m_marketDataManager.getTradeData(bean.getPair());
		m_marketDataPusher.addTradePushPair(data);
		return true;
	}
	
	
	// 获取TRade订阅者集合(浅克隆)
	public Map<String, SubDataBean> getAllCallBackTradeByNoCopy(String pair) {
		if (m_tradeDataCallBackSubMap.containsKey(pair)) {
			ConcurrentHashMap<String, SubDataBean> result = new ConcurrentHashMap<String, SubDataBean>();
			ConcurrentHashMap<String, SubDataBean> queue = m_tradeDataCallBackSubMap.get(pair);
			result.putAll(queue);
			return result;
		} else {
			return null;
		}
	}
	


	// 获取KLine订阅者集合(深克隆)
	public Map<String, SubKLineData> getAllCallBackKLine(Integer key) {
		if (m_klineDataCallBackSubMap.containsKey(key)) {
			ConcurrentHashMap<String, SubKLineData> result = new ConcurrentHashMap<String, SubKLineData>();
			ConcurrentHashMap<String, SubKLineData> queue = m_klineDataCallBackSubMap.get(key);
			Iterator<Map.Entry<String, SubKLineData>> iter = queue.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, SubKLineData> entry = iter.next();
				SubKLineData newBean = entry.getValue().copy();
				result.put(newBean.m_sid, newBean);
			}
			return result;
		} else {
			return null;
		}
	}

	// 获取KLine订阅者集合(浅克隆)
	public Map<String, SubKLineData> getAllCallBackKLineByNoCopy(Integer key) {
		if (m_klineDataCallBackSubMap.containsKey(key)) {
			ConcurrentHashMap<String, SubKLineData> result = new ConcurrentHashMap<String, SubKLineData>();
			ConcurrentHashMap<String, SubKLineData> queue = m_klineDataCallBackSubMap.get(key);
			result.putAll(queue);
			return result;
		} else {
			return null;
		}
	}

	// 获取KLine订阅者
	public SubKLineData getCallBackKLine(Integer key, String sid) {
		if (m_klineDataCallBackSubMap.containsKey(key)) {
			SubKLineData bean = m_klineDataCallBackSubMap.get(key).get(sid);
			if (bean == null) {
				return null;
			} else {
				return bean.copy();
			}
		} else {
			return null;
		}
	}

	// 添加KLine订阅
	public boolean putCallBackKLine(Integer key, SubKLineData bean) {
		if (m_klineDataCallBackSubMap.containsKey(key)) {
			ConcurrentHashMap<String, SubKLineData> queue = m_klineDataCallBackSubMap.get(key);
			queue.put(bean.m_sid, bean);
		} else {
			ConcurrentHashMap<String, SubKLineData> map = new ConcurrentHashMap<String, SubKLineData>();
			map.put(bean.m_sid, bean);
			m_klineDataCallBackSubMap.put(key, map);
		}
		return true;
	}

	// 删除KLine订阅
	public boolean removeCallBackKLine(Integer key, String sid) {
		if (m_klineDataCallBackSubMap.containsKey(key)) {
			ConcurrentHashMap<String, SubKLineData> queue = m_klineDataCallBackSubMap.get(key);
			queue.remove(sid);
		}
		return true;
	}

	// 根据sid删除KLine订阅者
	public boolean removeCallBackKLine(String sid) {
		Iterator<Entry<Integer, ConcurrentHashMap<String, SubKLineData>>> iter = m_klineDataCallBackSubMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Integer, ConcurrentHashMap<String, SubKLineData>> entry = iter.next();
			ConcurrentHashMap<String, SubKLineData> subMap = entry.getValue();
			subMap.remove(sid);
		}
		return true;
	}
	
	//添加K线请求者
	public boolean putCallBackKLineReq(Integer hashKey, SubKLineData subData) {
		m_klineDataCallBackReqMap.put(hashKey, subData);
		return true;
	}
	
	//删除K线请求者
	public boolean removeCallBackKLineReq(Integer hashKey) {
		m_klineDataCallBackReqMap.remove(hashKey);
		return true;
	}
	
	//K线请求者
	public SubKLineData getCallBackKLineReq(Integer hashKey) {
		return m_klineDataCallBackReqMap.get(hashKey).copy();
	}
	
}
