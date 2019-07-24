package com.gaia.autotrade.http.controller2;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.gaia.autotrade.owsock.bean.BackInfo;
import com.gaia.autotrade.owsock.bean.JsonMessage;
import com.gaia.autotrade.owsock.manager.MarketDataManager;


@Controller
public class BaseInfoController {
	//获取所有交易对
	@RequestMapping(value="/v1/common/symbols",method = RequestMethod.GET)
	public void getAllSymbol() {
		MarketDataManager  manger=MarketDataManager.getInstance();
		List<String> tradePairList = manger.getTradePairList();	
		JsonMessage json=new JsonMessage();
		json.data=new ArrayList<BackInfo>();
		json.data.add(new BackInfo());
		
	}
	
	//获取所有币种
	@RequestMapping(value="/v1/common/currencys",method = RequestMethod.GET)
	public void getAllCurrencys() {
		
		
	}
	@Test
	//获取当前系统时间
	@RequestMapping(value="/v1/common/timestamp",method = RequestMethod.GET)
	public void getNowTime() {
		long currentTimeMillis = System.currentTimeMillis();
		JSON.toJSON(currentTimeMillis);
			
	}

}
