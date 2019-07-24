package com.gaia.autotrade.http.controller2;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.gaia.autotrade.http.bean.JsonMessage;
import com.gaia.autotrade.http.bean.SymbolInfo;
import com.gaia.autotrade.owsock.manager.MarketDataManager;
import com.gaia.autotrade.owsock.market_bean.SecurityInfo;


@Controller
public class BaseInfoController {
	//获取所有交易对
	@RequestMapping(value="/v1/common/symbols",method = RequestMethod.GET)
	public String getAllSymbol() {
		MarketDataManager  manager = MarketDataManager.getInstance();
		List<SecurityInfo> securityList = manager.getSecurityList();
		List<SymbolInfo> symbolList = new ArrayList<SymbolInfo>();
		for(SecurityInfo info : securityList) {
			SymbolInfo symbolInfo = new SymbolInfo();
			symbolInfo.setBaseCurrency(info.m_firstLeg);
			
			  symbolInfo.setQuotoCurrency(quotoCurrency);
			  symbolInfo.setPricePrecision(pricePrecision);
			  symbolInfo.setAmountPrecision(amountPrecision);
			  symbolInfo.setSymbolPartition(symbolPartition); 
			  symbolInfo.setSymbol(symbol);
			  symbolInfo.setState(state); 
			  symbolInfo.setValuePrecision(valuePrecision);
			  symbolInfo.setMinOrderAmt(minOrderAmt);
			  symbolInfo.setMaxOrderAmt(maxOrderAmt);
			  symbolInfo.setMinOrderValue(minOrderValue);
			  symbolInfo.setLeverageRatio(leverageRatio);
			 
		}
		
		JsonMessage jsonMsg = new JsonMessage();
		jsonMsg.setData(symbolList);
		jsonMsg.setStatus("ok");
		jsonMsg.setTs(System.currentTimeMillis());
		return JSON.toJSONString(jsonMsg);
	}
	
	//获取所有币种
	@RequestMapping(value="/v1/common/currencys",method = RequestMethod.GET)
	public void getAllCurrencys() {
		
		
	}
	
	@Test
	//获取当前系统时间
	@RequestMapping(value="/v1/common/timestamp",method = RequestMethod.GET)
	public String getNowTime() {	
		JsonMessage jsonMsg = new JsonMessage();
		jsonMsg.setData(System.currentTimeMillis());
		return JSON.toJSONString(jsonMsg);
		
			
	}

}
