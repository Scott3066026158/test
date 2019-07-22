package com.gaia.autotrade.ws.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaia.autotrade.owsock.manager.MarketDataManager;
import com.gaia.autotrade.ws.bean.WebSocketServletRequest;
import com.gaia.autotrade.ws.bean.WebSocketServletResponse;
import com.gaia.autotrade.ws.manager.WebSocketServiceManager;

@Component
public class MarketDepthService extends MarketBaseService {

	// 行情数据管理器
	private MarketDataManager m_mkDataManager = MarketDataManager.getInstance();
	
	// 设置服务Key
	public MarketDepthService() {
		setServiceKey("depth");
	}

	// 在服务管理器中注册
	@Autowired
	private void setWebSocketServiceManager(WebSocketServiceManager wsSerManager) {
		wsSerManager.addService(this);
	}

	@Override
	public int RevWsSub(WebSocketServletRequest request, WebSocketServletResponse response) {
		Map<String, String> params = request.getParams();
		String pair = params.get("pair");
		String sid = params.get("sid");
		String param = params.get("param");
		
		
		return 0;
	}

	@Override
	public int RevWsReq(WebSocketServletRequest request, WebSocketServletResponse response) {
		revNoProvideReq(request, response);
		return 0;
	}
}
