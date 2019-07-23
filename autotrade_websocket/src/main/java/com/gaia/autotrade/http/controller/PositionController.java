package com.gaia.autotrade.http.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//资产接口
@RestController
@RequestMapping(value = "api/v1")
public class PositionController {
	
	//获取用户账户信息
	@RequestMapping(value = "/contract_account_info",method = RequestMethod.POST)
	public String getAccountInfo(@RequestParam(value = "symbol",required = false)String symbol){
		
		return null;
	}
	
	//获取用户持仓信息
	@RequestMapping(value = "/contract_position_info",method = RequestMethod.POST)
	public String getPositionInfo(@RequestParam(value = "symbol",required = false)String symbol){
		
		return null;
	}
	

}
