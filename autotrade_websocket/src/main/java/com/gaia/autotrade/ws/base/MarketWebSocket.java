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
import com.gaia.autotrade.ws.bean.ResponseMsg;
import com.gaia.autotrade.ws.controller.ErrorController;
import com.gaia.autotrade.ws.manager.WebSocketControllerManager;
import com.gaia.autotrade.ws.manager.WebSocketSessionManager;
import com.gaia.autotrade.ws.manager.WebSocketSubManager;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

@ServerEndpoint("/ws")
@Component
public class MarketWebSocket {

	// 获取日志
	private static Log log = LogFactory.get(MarketWebSocket.class);
	// 会话管理器
	public static WebSocketSessionManager m_wsSenManager;
	// 控制管理器
	public static WebSocketControllerManager m_wsConManager;
	// 订阅者管理器
	public static WebSocketSubManager m_subDataManager;

	private Session m_session;

	public MarketWebSocket() {
		System.out.println(this.getClass().getName() + "对象被创建!");
	}

	@Autowired
	public void setWebSocketSessionManager(WebSocketSessionManager wsSenManager) {
		MarketWebSocket.m_wsSenManager = wsSenManager;
	}

	@Autowired
	public void setWebSocketControllerManager(WebSocketControllerManager wsConManager) {
		MarketWebSocket.m_wsConManager = wsConManager;
	}
	
	@Autowired
	public void setWebSocketSubManager(WebSocketSubManager subDataManager) {
		MarketWebSocket.m_subDataManager = subDataManager;
	}

	@OnOpen
	public void onOpen(Session session) {
		m_session = session;
		m_wsSenManager.addWebSocket(this);
	}

	@OnClose
	public void onClose(Session session) {
		m_wsSenManager.removeWebSocket(getId());
		m_subDataManager.removeCallBackDepth(getId());
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		try {
			JSONObject msg = JSONObject.parseObject(message);
			msg.put("sid", session.getId());
			ResponseMsg resp;
			if (msg.getString("ping") != null) {
				resp = m_wsConManager.getController("ping").onReceive(msg);
			} else if (msg.getString("req") != null) {
				resp = m_wsConManager.getController("req").onReceive(msg);
			} else if (msg.getString("sub") != null) {
				resp = m_wsConManager.getController("sub").onReceive(msg);
			} else {
				ErrorController err = ErrorController.getInstance();
				resp = err.onReceive(msg, "未查找到服务");
			}
			String result = sendMsg(resp);
			log.info("Rev,SID:" + msg.toString());
			log.info("Send,SID:" + result);
		} catch (JSONException e) {
			ErrorController err = ErrorController.getInstance();
			ResponseMsg msg = err.onReceive(message, e);
			sendMsg(msg);
			log.error("接收到错误的Message:" + message);
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {
		m_wsSenManager.removeWebSocket(getId());
		m_subDataManager.removeCallBackDepth(getId());
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
	 * @param msg 需要发送的对象
	 * @return 发送成功返回字符串，失败返回null
	 */
	public String sendMsg(ResponseMsg msg) {
		try {
			String text = JSON.toJSONString(msg);
			m_session.getBasicRemote().sendText(text);
			
			return text;
		} catch (IOException e) {
			log.error("Message发送失败!");
			return null;
		} catch (Exception e){
			e.printStackTrace();
			log.error("Message发送失败!");
			return null;
		}
	}

}
