package com.gaia.autotrade.owsock.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.gaia.autotrade.owsock.bean.SecurityInfo;
import com.gaia.autotrade.owsock.bean.MarketDepthData;
import com.gaia.autotrade.owsock.bean.MarketTickData;
import com.gaia.autotrade.owsock.bean.MarketUserInfo;

public class MarketDataManager {

	private static MarketDataManager m_self = null;
	// 行情登录用户信息
	private MarketUserInfo m_userInfo = new MarketUserInfo();
	// 缓存Depth行情数据
	private ConcurrentHashMap<String, MarketDepthData> m_depthDataMap = new ConcurrentHashMap<String, MarketDepthData>();
	// 缓存Tick行情数据
	private ConcurrentHashMap<String, MarketTickData> m_tickDataMap = new ConcurrentHashMap<String, MarketTickData>();
	// 合约信息表
	public ConcurrentHashMap<String, SecurityInfo> m_securitiesMap = new ConcurrentHashMap<String, SecurityInfo>();
	// 交易对子表
	public ConcurrentHashMap<String, String> m_tradePairMap = new ConcurrentHashMap<String, String>();

	// 私有化构造函数
	private MarketDataManager() {

	}

	// 获取自身
	public static MarketDataManager getInstance() {
		if (null == m_self) {
			synchronized (MarketDataManager.class) {
				if (null == m_self) {
					m_self = new MarketDataManager();
				}
			}
		}
		return m_self;
	}

	// 获取行情用户数据
	public MarketUserInfo getMarketUserInfo() {
		return m_userInfo;
	}

	/**
	 * 获取指定交易对子Depth Data
	 * 
	 * @param pair key
	 * @return 存在返回数据，不存在返回null
	 */
	public MarketDepthData getDepthData(String pair) {
		MarketDepthData data = m_depthDataMap.get(pair);
		return data.copy();
	}

	/**
	 * 获取所有交易对子Depth Data
	 * 
	 * @return 返回所有
	 */
	public List<MarketDepthData> getDepthDatas() {
		ArrayList<MarketDepthData> result = new ArrayList<MarketDepthData>();
		Iterator<Entry<String, MarketDepthData>> iter = m_depthDataMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, MarketDepthData> entry = (Map.Entry<String, MarketDepthData>) iter.next();
			MarketDepthData val = entry.getValue();
			result.add(val.copy());
		}
		return result;
	}

	/**
	 * 添加或更新对应的交易对子的Depth Data
	 * 
	 * @param data 最新的Depth Data
	 */
	public void putDepthData(MarketDepthData data) {
		m_depthDataMap.put(data.m_code, data);
	}

	/**
	 * 获取指定交易对子Depth Data
	 * 
	 * @param pair key
	 * @return 存在返回数据，不存在返回null
	 */
	public MarketTickData getTickData(String pair) {
		MarketTickData data = m_tickDataMap.get(pair);
		return data.copy();
	}

	/**
	 * 获取所有交易对子Depth Data
	 * 
	 * @return 返回所有
	 */
	public List<MarketTickData> getTickDatas() {
		ArrayList<MarketTickData> result = new ArrayList<MarketTickData>();
		Iterator<Entry<String, MarketTickData>> iter = m_tickDataMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, MarketTickData> entry = (Map.Entry<String, MarketTickData>) iter.next();
			MarketTickData val = entry.getValue();
			result.add(val.copy());
		}
		return result;
	}

	/**
	 * 添加或更新对应的交易对子的Tick数据
	 * 
	 * @param data 最新的Tick数据
	 */
	public void putTickData(MarketTickData data) {
		m_tickDataMap.put(data.m_code, data);
	}

	/**
	 * 填充所有所有合约的信息，在行情启动的时候接收
	 * 
	 * @param securityList 所有的合约信息
	 */
	public void putSecurityList(ArrayList<SecurityInfo> securityList) {
		for (int i = 0; i < securityList.size(); i++) {
			SecurityInfo security = securityList.get(i);
			m_securitiesMap.put(security.m_code.toUpperCase(), security);
		}
	}

	/**
	 * 通过合约Code获取合约信息
	 * 
	 * @param securityCode
	 * @return 存在获取，不存在返回null
	 */
	public SecurityInfo getSecurity(String securityCode) {
		SecurityInfo info = m_securitiesMap.get(securityCode);
		return info.copy();
	}

	/**
	 * 获取所有的合约信息
	 * 
	 * @return 返回所有的合约List
	 */
	public List<SecurityInfo> getSecurityList() {
		ArrayList<SecurityInfo> result = new ArrayList<SecurityInfo>();
		Iterator<Entry<String, SecurityInfo>> iter = m_securitiesMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, SecurityInfo> entry = (Map.Entry<String, SecurityInfo>) iter.next();
			SecurityInfo val = entry.getValue();
			result.add(val.copy());
		}
		return result;
	}

	/**
	 * 填充所有所有交易对子，在行情启动的时候接收
	 * 
	 * @param securityList 所有的交易对子
	 */
	public void putTradePairList(ArrayList<String> tradePairList) {
		for (int i = 0; i < tradePairList.size(); i++) {
			String tradePair = tradePairList.get(i);
			m_tradePairMap.put(tradePair.toUpperCase(), tradePair.toUpperCase());
		}
	}
	
	/**
	 * 获取所有的交易对子
	 * 
	 * @return 返回交易对子List
	 */
	public List<String> getTradePairList(){
		ArrayList<String> result = new ArrayList<String>();
		Iterator<Entry<String, String>> iter = m_tradePairMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
			String val = entry.getValue();
			result.add(new String(val));
		}
		return result;
	}
}
