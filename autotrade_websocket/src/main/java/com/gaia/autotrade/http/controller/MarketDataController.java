package com.gaia.autotrade.http.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.gaia.autotrade.http.entity.JsonMessage;
import com.gaia.autotrade.owsock.manager.MarketDataManager;
import com.gaia.autotrade.owsock.market_bean.MarketTickDetailData;
import com.gaia.autotrade.ws.bean.TickDetailPushTick;

@RestController
public class MarketDataController {

	public MarketDataManager m_marketDataManager = MarketDataManager.getInstance();

	@RequestMapping(value = "/market/tickers", method = RequestMethod.POST)
	public String get(@RequestParam(value = "symbol") String symbol) {
		JsonMessage jsonMsg = new JsonMessage();
		if (!m_marketDataManager.isExistPair(symbol)) {
			jsonMsg.setCode("301");
			jsonMsg.setData(null);
			jsonMsg.setErrMsg("交易对子错误");
		}
		MarketTickDetailData data = m_marketDataManager.getTickData(symbol);
		TickDetailPushTick tickData = data.copyToTickDetail();
		jsonMsg.setData(tickData);
		jsonMsg.setCode("200");
		return JSON.toJSONString(jsonMsg);
	}
}
