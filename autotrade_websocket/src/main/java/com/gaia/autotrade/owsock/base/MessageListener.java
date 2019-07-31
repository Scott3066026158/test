package com.gaia.autotrade.owsock.base;

import com.gaia.autotrade.owsock.base.CMessage;
import com.gaia.autotrade.owsock.base.ListenerMessageCallBack;

/**
 * 消息监听
 * 
 */
public class MessageListener {
	/**
	 * 创建消息监听
	 * 
	 */
	public MessageListener() {
	}

	/**
	 * 析构方法
	 * 
	 */
	protected void finalize() throws Throwable {
		Clear();
	}

	/**
	 * 监听回调列表
	 * 
	 */
	private java.util.ArrayList<ListenerMessageCallBack> m_callBacks = new java.util.ArrayList<ListenerMessageCallBack>();

	/**
	 * 添加回调
	 * 
	 * @param callBack 回调
	 */
	public final void Add(ListenerMessageCallBack callBack) {
		m_callBacks.add(callBack);
	}

	/**
	 * 回调方法
	 * 
	 */
	public final void CallBack(CMessage message) {
		int callBackSize = m_callBacks.size();
		for (int i = 0; i < callBackSize; i++) {
			m_callBacks.get(i).CallListenerMessageEvent(message);
		}
	}

	/**
	 * 清除监听
	 * 
	 */
	public final void Clear() {
		m_callBacks.clear();
	}

	/**
	 * 移除回调
	 * 
	 * @param callBack 回调
	 */
	public final void Remove(ListenerMessageCallBack callBack) {
		m_callBacks.remove(callBack);
	}
}