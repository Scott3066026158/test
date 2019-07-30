package com.gaia.autotrade.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.gaia.autotrade.ws.bean.ResponseMsg;
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
	public ResponseMsg onReceive(JSONObject message) {
		long ts = System.currentTimeMillis();
		try {
			long ping = (long) message.get("ping");
			ResponseMsg msg = new ResponseMsg();
			msg.setPong(ping);
			return msg;
		} catch (ClassCastException e) {
			ResponseMsg msg = new ResponseMsg();
			msg.setPong(ts);
			return msg;
		} catch (Exception e) {
			ResponseMsg msg = new ResponseMsg();
			msg.setPong(ts);
			return msg;
		}
	}

}
