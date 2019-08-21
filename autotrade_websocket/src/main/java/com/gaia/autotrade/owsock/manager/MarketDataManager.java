package com.gaia.autotrade.owsock.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaia.autotrade.owsock.market_bean.MarketDepthData;
import com.gaia.autotrade.owsock.market_bean.MarketTickDetailData;
import com.gaia.autotrade.owsock.market_bean.MarketTradeDetailData;
import com.gaia.autotrade.owsock.market_bean.MarketUserInfo;
import com.gaia.autotrade.owsock.market_bean.SecurityInfo;
import com.gaia.autotrade.ws.bean.KLineCallBackData;
import com.gaia.autotrade.ws.manager.WebSocketMarketDataPusher;
import com.gaia.autotrade.ws.manager.WebSocketSubManager;

@Component
public class MarketDataManager {

	private static MarketDataManager m_self = null;

	// Ws订阅者管理器
	private WebSocketSubManager m_subDataManager;
	// Ws推送者管理器
	private WebSocketMarketDataPusher m_pushDataManager;
	// 行情登录用户信息
	private MarketUserInfo m_userInfo = new MarketUserInfo();
	// 缓存Depth行情数据
	private ConcurrentHashMap<String, MarketDepthData> m_depthDataMap = new ConcurrentHashMap<String, MarketDepthData>();
	// 缓存Tick行情数据
	private ConcurrentHashMap<String, MarketTickDetailData> m_tickDataMap = new ConcurrentHashMap<String, MarketTickDetailData>();
	// 缓存Trade行情数据
	private ConcurrentHashMap<String, MarketTradeDetailData> m_tradeDataMap = new ConcurrentHashMap<String, MarketTradeDetailData>();
	// 缓存KLine行情数据Map<交易对子+分钟线, KLine数据>
	private ConcurrentHashMap<Integer, KLineCallBackData> m_klineDataMap = new ConcurrentHashMap<Integer, KLineCallBackData>();
	// 合约信息表
	private ConcurrentHashMap<String, SecurityInfo> m_securitiesMap = new ConcurrentHashMap<String, SecurityInfo>();
	// 交易对子表
	private ConcurrentHashMap<String, String> m_tradePairMap = new ConcurrentHashMap<String, String>();
	// 币种表
	private ConcurrentHashMap<String, String> m_coinMap = new ConcurrentHashMap<String, String>();

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

	// 设置订阅者
	@Autowired
	public void setWebSocketSubManager(WebSocketSubManager webSocketSubManager) {
		m_subDataManager = webSocketSubManager;
	}

	// 设置推送者
	@Autowired
	public void setWebSocketMarketDataPusher(WebSocketMarketDataPusher pushDataManager) {
		m_pushDataManager = pushDataManager;
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
	 * 添加或更新对应的交易对子的Depth Data 同时通知推送者推送数据
	 * 
	 * @param data 最新的Depth Data
	 */
	public void putDepthData(MarketDepthData data) {
		m_depthDataMap.put(data.m_lowCode, data);
		MarketDepthData copyData = data.copy();
		m_pushDataManager.addDepthPushPair(copyData);
	}

	/**
	 * 获取指定交易对子Kline Data
	 * 
	 * @param pair key
	 * @return 存在返回数据，不存在返回null
	 */
	public KLineCallBackData getKLineData(Integer hashCode) {
		KLineCallBackData data = m_klineDataMap.get(hashCode);
		return data.copy();
	}

	/**
	 * 获取所有交易对子时间线Kline Data
	 * 
	 * @return 返回所有
	 */
	public List<KLineCallBackData> getKLineDatas() {
		ArrayList<KLineCallBackData> result = new ArrayList<KLineCallBackData>();
		Iterator<Entry<Integer, KLineCallBackData>> iter = m_klineDataMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, KLineCallBackData> entry = (Map.Entry<Integer, KLineCallBackData>) iter.next();
			KLineCallBackData val = entry.getValue();
			result.add(val.copy());
		}
		return result;
	}

	/**
	 * 添加或更新对应的交易对子的Tick数据
	 * 
	 * @param data 最新的KLine数据
	 */
	public void putKLineData(KLineCallBackData data) {
		m_klineDataMap.put(data.hashCode(), data);
		if (data.getSubData().m_subscription != 0) {
			m_pushDataManager.addKLineReqPushPair(data);
		} else {
			m_pushDataManager.addKLinePushPair(data);
		}

	}

	/**
	 * 获取指定交易对子KLine Data
	 * 
	 * @param pair key
	 * @return 存在返回数据，不存在返回null
	 */
	public MarketTickDetailData getTickData(String key) {
		MarketTickDetailData data = m_tickDataMap.get(key);
		return data.copy();
	}

	/**
	 * 获取所有交易对子Tick Data
	 * 
	 * @return 返回所有
	 */
	public List<MarketTickDetailData> getTickDatas() {
		ArrayList<MarketTickDetailData> result = new ArrayList<MarketTickDetailData>();
		Iterator<Entry<String, MarketTickDetailData>> iter = m_tickDataMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, MarketTickDetailData> entry = (Map.Entry<String, MarketTickDetailData>) iter.next();
			MarketTickDetailData val = entry.getValue();
			result.add(val.copy());
		}
		return result;
	}

	/**
	 * 添加或更新对应的交易对子的Tick数据
	 * 
	 * @param data 最新的Tick数据
	 */
	public void putTickData(MarketTickDetailData data) {
		m_tickDataMap.put(data.m_lowCode, data);
		MarketTickDetailData copyData = data.copy();
		m_pushDataManager.addTickPushPair(copyData);
	}

	/**
	 * 添加或更新对应的交易对子的Trade数据
	 * 
	 * @param data 最新的Trade数据
	 */
	public void putTradeData(MarketTradeDetailData data) {
		m_tradeDataMap.put(data.m_lowpair, data);
		m_pushDataManager.addTradePushPair(data.copy());
	}

	/**
	 * 获取指定交易对子Trade Data
	 * 
	 * @param pair key
	 * @return 存在返回数据，不存在返回null
	 */
	public MarketTradeDetailData getTradeData(String pair) {
		MarketTradeDetailData data = m_tradeDataMap.get(pair);
		return data.copy();
	}

	/**
	 * 获取所有交易对子Trade Data
	 * 
	 * @return 返回所有
	 */
	public List<MarketTradeDetailData> getTradeDatas() {
		ArrayList<MarketTradeDetailData> result = new ArrayList<MarketTradeDetailData>();
		Iterator<Entry<String, MarketTradeDetailData>> iter = m_tradeDataMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, MarketTradeDetailData> entry = (Map.Entry<String, MarketTradeDetailData>) iter.next();
			MarketTradeDetailData val = entry.getValue();
			result.add(val.copy());
		}
		return result;
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
			String value = tradePairList.get(i);
			if (value == null) {
				continue;
			}
			String key = value.replace("/", "");
			m_tradePairMap.put(key.toLowerCase(), value);
		}
	}

	/**
	 * 获取所有的交易对子
	 * 
	 * @return 返回交易对子List
	 */
	public List<String> getTradePairList() {
		ArrayList<String> result = new ArrayList<String>();
		Iterator<Entry<String, String>> iter = m_tradePairMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
			String val = entry.getValue();
			result.add(new String(val));
		}
		return result;
	}

	/**
	 * 获取交易对子
	 * 
	 * @return 返回交易对子
	 */
	public String getTradePair(String key) {
		return new String(m_tradePairMap.get(key));
	}

	/**
	 * 检测交易对子是否存在
	 * 
	 * @param pair交易对子
	 * @return 存在返回true 不存在返回false
	 */
	public boolean isExistPair(String pair) {
		if (pair == null) {
			return false;
		}
		return m_tradePairMap.containsKey(pair);
	}

	/**
	 * put coinCode
	 * 
	 * @param coinCode 币种
	 * @return true
	 */
	public boolean putCoinCode(String coinCode) {
		m_coinMap.put(coinCode, coinCode);
		return true;
	}

	/**
	 * 获取所有币种列表
	 * 
	 * @return 币种列表
	 */
	public List<String> getCoinCodeList() {
		List<String> result = new ArrayList<String>();
		Iterator<Entry<String, String>> iter = m_coinMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
			String val = entry.getValue();
			result.add(new String(val));
		}
		return result;
	}
}
