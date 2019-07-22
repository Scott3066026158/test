package com.gaia.autotrade.ws.base;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.gaia.autotrade.ws.bean.ResponseMsgInterfact;
import com.gaia.autotrade.ws.controller.ErrorController;
import com.gaia.autotrade.ws.manager.WebSocketControllerManager;
import com.gaia.autotrade.ws.manager.WebSocketSessionManager;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

@ServerEndpoint("/ws")
@Component
public class MarketWebSocket {

	// 获取日志
	private static Log log = LogFactory.get(MarketWebSocket.class);
	// 会话管理器
	public static WebSocketSessionManager<MarketWebSocket> m_wsSenManager;
	// 控制管理器
	public static WebSocketControllerManager m_wsConManager;

	private Session m_session;

	public MarketWebSocket() {
		System.out.println(this.getClass().getName() + "对象被创建!");
	}

	@Autowired
	public void setWebSocketSessionManager(WebSocketSessionManager<MarketWebSocket> wsSenManager) {
		MarketWebSocket.m_wsSenManager = wsSenManager;
	}

	@Autowired
	public void setWebSocketControllerManager(WebSocketControllerManager wsConManager) {
		MarketWebSocket.m_wsConManager = wsConManager;
	}

	@OnOpen
	public void onOpen(Session session) {
		m_session = session;
		m_wsSenManager.addWebSocket(this);
	}

	@OnClose
	public void onClose(Session session) {
		m_wsSenManager.removeWebSocket(session.getId());
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		try {
			JSONObject msg = JSONObject.parseObject(message);
			msg.put("sid", session.getId());
			ResponseMsgInterfact resp;
			if (m_wsConManager.getController("ping") != null) {
				resp = m_wsConManager.getController("ping").onReceive(msg);
			} else if (m_wsConManager.getController("req") != null) {
				resp = m_wsConManager.getController("req").onReceive(msg);
			} else if (m_wsConManager.getController("sub") != null) {
				resp = m_wsConManager.getController("sub").onReceive(msg);
			} else {
				ErrorController err = ErrorController.getInstance();
				resp = err.onReceive(msg, "未查找到服务");
			}
			sendMsg(resp);
			log.info("Rev,SID:" + session.getId() + msg.toString());
		} catch (JSONException e) {
			ErrorController err = ErrorController.getInstance();
			ResponseMsgInterfact msg = err.onReceive(message, e);
			sendMsg(msg);
			log.error("接收到错误的Message:" + message);
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {
		log.error("连接错误,SID:" + session.getId());
	}

	/**
	 * @return 获取当前SessionID
	 */
	public String getId() {
		if (m_session == null)
			return null;
		return m_session.getId();
	}

	/**
	 * 发送Msg
	 * 
	 * @param msg 需要发送的对象
	 */
	public boolean sendMsg(ResponseMsgInterfact msg) {
		try {
			String text = (String) JSON.toJSON(msg);
			m_session.getBasicRemote().sendText(text);
			return true;
		} catch (IOException e) {
			log.error("Message发送失败!");
			return false;
		}
	}

}
