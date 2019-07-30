package com.gaia.autotrade.ws.manager;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.gaia.autotrade.ws.base.MarketWebSocket;

@Component
public class WebSocketSessionManager {

	// 记录此对象生成次数
	private static int m_createCount = 0;
	// 管理的所有Session
	private ConcurrentHashMap<String, MarketWebSocket> m_sessionMap = new ConcurrentHashMap<String, MarketWebSocket>();

	public WebSocketSessionManager() {
		m_createCount++;
		if (m_createCount >= 2) {
			System.out.println("此对象被生成了两次，程序退出，查问题!");
			System.exit(0);
		}
	}

	/**
	 * 添加Session
	 * 
	 * @param value 需要添加的WS的Session
	 * @return 有就替换，没有添加
	 */
	public boolean addWebSocket(MarketWebSocket value) {
		MarketWebSocket oldValue = m_sessionMap.put(value.getId(), value);
		if (oldValue == null)
			return true;
		return true;
	}

	/**
	 * 根据SessionID删除Session
	 * 
	 * @param key SessionID
	 * @return 不存在返回false，删除成功返回true
	 */
	public boolean removeWebSocket(String key) {
		boolean flag = m_sessionMap.containsKey(key);
		if (flag == false)
			return flag;
		m_sessionMap.remove(key);
		return true;
	}

	/**
	 * 根据SessionID获取Session
	 * 
	 * @param key SessionID
	 * @return 获取成功返回Session，失败返回null
	 */
	public MarketWebSocket getWebSocket(String key) {
		boolean flag = m_sessionMap.containsKey(key);
		if (flag == false)
			return null;
		return m_sessionMap.get(key);
	}

}
