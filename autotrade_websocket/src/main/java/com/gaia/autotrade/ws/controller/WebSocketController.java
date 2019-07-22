package com.gaia.autotrade.ws.controller;

import com.alibaba.fastjson.JSONObject;
import com.gaia.autotrade.ws.bean.ResponseMsgInterfact;

public abstract class WebSocketController {

	// ControllerKey，可在服务管理器中根据key获取到对应的服务
	private String controllerKey;

	public String getControllerKey() {
		return controllerKey;
	}

	public void setControllerKey(String controllerKey) {
		this.controllerKey = controllerKey;
	}

	public abstract ResponseMsgInterfact onReceive(JSONObject message);

}
