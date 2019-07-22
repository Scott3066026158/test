package com.gaia.autotrade.ws.manager;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.gaia.autotrade.ws.service.MarketBaseService;
import com.gaia.autotrade.ws.service.MarketKLineService;

@Component
public class WebSocketServiceManager {

	// 管理的所有Service
	private ConcurrentHashMap<String, MarketBaseService> m_serviceMap = new ConcurrentHashMap<String, MarketBaseService>();

	// 构造函数
	public WebSocketServiceManager() {
		MarketKLineService klineService = new MarketKLineService();
		m_serviceMap.put(klineService.getServiceKey(), klineService);
	}

	/**
	 * 添加Service
	 * 
	 * @param value 需要添加的WS的Service
	 * @return 有就替换，没有添加，恒定返回true
	 */
	public boolean addService(MarketBaseService value) {
		MarketBaseService oldValue = m_serviceMap.put(value.getServiceKey(), value);
		if (oldValue == null)
			return true;
		return true;
	}

	/**
	 * 根据ServiceKey删除Service
	 * 
	 * @param key ServiceKey
	 * @return 不存在返回false，删除成功返回true
	 */
	public boolean removeService(String key) {
		boolean flag = m_serviceMap.containsKey(key);
		if (flag == false)
			return flag;
		m_serviceMap.remove(key);
		return true;
	}

	/**
	 * 根据ServiceKey获取Service
	 * 
	 * @param key ServiceKey
	 * @return 获取成功返回Service，失败返回null
	 */
	public MarketBaseService getService(String key) {
		boolean flag = m_serviceMap.containsKey(key);
		if (flag == false)
			return null;
		return m_serviceMap.get(key);
	}

}
