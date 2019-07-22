package com.gaia.autotrade.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaia.autotrade.ws.bean.WebSocketServletRequest;
import com.gaia.autotrade.ws.bean.WebSocketServletResponse;
import com.gaia.autotrade.ws.manager.WebSocketServiceManager;

/**
 * 处理K线数据
 * 
 * @author GAIA
 *
 */
@Component
public class MarketKLineService extends MarketBaseService {

	// 设置服务Key
	public MarketKLineService() {
		setServiceKey("kline");
	}

	// 在服务管理器中注册
	@Autowired
	private void setWebSocketServiceManager(WebSocketServiceManager wsSerManager) {
		wsSerManager.addService(this);
	}

	@Override
	public int RevWsSub(WebSocketServletRequest request, WebSocketServletResponse response) {
		System.out.println(this.getClass().getName() + "接收到订阅");
		return 0;
	}

	@Override
	public int RevWsReq(WebSocketServletRequest request, WebSocketServletResponse response) {
		return 0;
	}

}
