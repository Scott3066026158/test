package com.gaia.autotrade;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.gaia.autotrade.owsock.bean.MarketUserInfo;
import com.gaia.autotrade.ws.base.MarketWebSocket;
import com.gaia.autotrade.ws.manager.WebSocketServiceManager;
import com.gaia.autotrade.ws.manager.WebSocketSessionManager;
import com.gaia.autotrade.ws.service.MarketBaseService;

@Configuration
public class Config {

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

}
