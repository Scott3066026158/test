package com.gaia.autotrade.ws.manager;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import com.gaia.autotrade.ws.bean.SubDataBean;

@Component
public class WebSocketSubManager {
	// 订阅的目标 Depth Tick KLine Other
	// 订阅的交易对子 AE/BIC BTC/USDT
	// 订阅者 SID
	// 订阅推送的规则 即到即发 间隔发送

	private static CopyOnWriteArrayList<WebSocketSubManager> m_dependList = new CopyOnWriteArrayList<WebSocketSubManager>();

	/**
	 * 添加依赖
	 * 
	 * @param 各个对象中此对象的引用
	 */
	public static void addDepend(WebSocketSubManager o) {
		m_dependList.add(o);
	}

	/**
	 * 对象被创建后为各个引用添加依赖
	 */
	public WebSocketSubManager() {
		for (@SuppressWarnings("unused")
		WebSocketSubManager reference : m_dependList) {
			reference = this;
		}
	}

	// Depth数据回调订阅者 (即到即发)
	private ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>> m_depthDataCallBackSubMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>>();
	// Tick数据回调订阅者 (即到即发)
	private ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>> m_tickDataCallBackSubMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>>();

	// Depth数据回调订阅者 (间隔发送)
	private ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>> m_depthDataIntervalSubMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>>();
	// Tick数据回调订阅者 (间隔发送)
	private ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>> m_tickDataIntervalSubMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, SubDataBean>>();

	// 获取深度订阅者集合
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

}
