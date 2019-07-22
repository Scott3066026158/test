package com.gaia.autotrade.ws.manager;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.gaia.autotrade.ws.controller.WebSocketController;

@Component
public class WebSocketControllerManager {

	// 管理的所有Controller
	private ConcurrentHashMap<String, WebSocketController> m_controllerMap = new ConcurrentHashMap<String, WebSocketController>();

	/**
	 * 添加Controller
	 * 
	 * @param value 需要添加的WS的Controller
	 * @return 有就替换，没有添加，恒定返回true
	 */
	public boolean addController(WebSocketController value) {
		WebSocketController oldValue = m_controllerMap.put(value.getControllerKey(), value);
		if (oldValue == null)
			return true;
		return true;
	}

	/**
	 * 根据ControllerKey删除Controller
	 * 
	 * @param key ControllerKey
	 * @return 不存在返回false，删除成功返回true
	 */
	public boolean removeController(String key) {
		boolean flag = m_controllerMap.containsKey(key);
		if (flag == false)
			return flag;
		m_controllerMap.remove(key);
		return true;
	}

	/**
	 * 根据ControllerKey获取Controller
	 * 
	 * @param key ControllerKey
	 * @return 获取成功返回Service，失败返回null
	 */
	public WebSocketController getController(String key) {
		boolean flag = m_controllerMap.containsKey(key);
		if (flag == false)
			return null;
		return m_controllerMap.get(key);
	}
}
