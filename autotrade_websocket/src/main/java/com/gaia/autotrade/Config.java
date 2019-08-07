package com.gaia.autotrade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.alibaba.fastjson.JSONObject;
import com.gaia.autotrade.owsock.market_bean.MarketUserInfo;
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
