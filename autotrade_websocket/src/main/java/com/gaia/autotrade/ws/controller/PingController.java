package com.gaia.autotrade.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.gaia.autotrade.ws.bean.ResponseMsgInterfact;
import com.gaia.autotrade.ws.bean.ResponsePingMsg;
import com.gaia.autotrade.ws.manager.WebSocketControllerManager;

@Component
public class PingController extends WebSocketController {

	public PingController() {
		setControllerKey("ping");
	}

	// 在控制管理器中注册
	@Autowired
	private void setWebSocketControllerManager(WebSocketControllerManager wsConManager) {
		wsConManager.addController(this);
	}

	@Override
	public ResponseMsgInterfact onReceive(JSONObject message) {
		long ts = System.currentTimeMillis();
		try {
			long ping = (long) message.get("ping");
			ResponsePingMsg msg = new ResponsePingMsg();
			msg.setPong(ping);
			return msg;
		} catch (ClassCastException e) {
			ResponsePingMsg msg = new ResponsePingMsg();
			msg.setPong(ts);
			return msg;
		} catch (Exception e) {
			ResponsePingMsg msg = new ResponsePingMsg();
			msg.setPong(ts);
			return msg;
		}
	}

}
