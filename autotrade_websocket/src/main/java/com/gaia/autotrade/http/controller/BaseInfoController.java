package com.gaia.autotrade.http.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gaia.autotrade.Applicaton;
import com.gaia.autotrade.http.entity.JsonMessage;
import com.gaia.autotrade.http.entity.SymbolInfo;
import com.gaia.autotrade.http.util.ConvertUtil;
import com.gaia.autotrade.owsock.manager.MarketDataManager;
import com.gaia.autotrade.owsock.market_bean.SecurityInfo;

/**
 * Base信息控制器
 * 
 * @author GAIA
 *
 */
@RestController
public class BaseInfoController {

	// 行情数据管理器
	private MarketDataManager m_marketDataManager = MarketDataManager.getInstance();

	// 配置文件
	private JSONObject m_errCode = Applicaton.getErrorCode();

	// 获取所有交易对
	@RequestMapping(value = "/v1/common/symbols", method = RequestMethod.GET)
	public String getAllSymbol() {
		List<SecurityInfo> securityList = m_marketDataManager.getSecurityList();
		List<SymbolInfo> symbolList = new ArrayList<SymbolInfo>();
		for (SecurityInfo info : securityList) {
			SymbolInfo symbolInfo = new SymbolInfo();
			symbolInfo.setBaseCurrency(info.m_firstLeg);
			symbolInfo.setQuotoCurrency(info.m_secondLeg);
			symbolInfo.setPricePrecision(ConvertUtil.tickToDigit(info.m_priceTick));
			symbolInfo.setAmountPrecision(ConvertUtil.tickToDigit(info.m_amountTick));
			symbolInfo.setSymbol(info.m_firstLeg + info.m_secondLeg);
			symbolInfo.convertStatusToState(info.m_status);
			symbolList.add(symbolInfo);
		}
		JsonMessage jsonMsg = new JsonMessage();
		jsonMsg.setData(symbolList);
		jsonMsg.setStatus("ok");
		jsonMsg.setCh("");
		jsonMsg.setTs(System.currentTimeMillis());
		return JSON.toJSONString(jsonMsg);
	}

	// 获取所有币种
	@RequestMapping(value = "/v1/common/currencys", method = RequestMethod.GET)
	public String getAllCurrencys() {
		List<String> coinCodes = m_marketDataManager.getCoinCodeList();
		JsonMessage jsonMsg = new JsonMessage();
		jsonMsg.setCh("");
		jsonMsg.setData(coinCodes);
		jsonMsg.setTs(System.currentTimeMillis());
		jsonMsg.setStatus("ok");
		return JSON.toJSONString(jsonMsg);
	}

	// 获取当前系统时间
	@RequestMapping(value = "/v1/common/timestamp", method = RequestMethod.GET)
	public String getTimeStamp() {
		JsonMessage jsonMsg = new JsonMessage();
		jsonMsg.setData(System.currentTimeMillis());
		return JSON.toJSONString(jsonMsg);
	}

	// 获取用户当前手续费率
	@RequestMapping(value = "/v1/fee/fee-rate/get", method = RequestMethod.GET)
	public String getUserRate() {
		JsonMessage jsonMsg = new JsonMessage();
		jsonMsg.setErrCode("701");
		jsonMsg.setTs(System.currentTimeMillis());
		jsonMsg.setStatus("error");
		jsonMsg.setErrMsg((String) m_errCode.get("701"));
		return JSON.toJSONString(jsonMsg);
	}
}
