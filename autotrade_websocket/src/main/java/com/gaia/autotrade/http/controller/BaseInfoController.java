package com.gaia.autotrade.http.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//基础信息接口
@CrossOrigin
@RestController
@RequestMapping(value = "api/v1")
public class BaseInfoController {

	// 获取合约信息
	@RequestMapping(value = "/contract_contract_info", method = RequestMethod.GET)
	public String getSecurityInfo(@RequestParam(value = "symbol", required = false) String symbol,
			@RequestParam(value = "contract_type", required = false) String contract_type,
			@RequestParam(value = "contract_code", required = false) String contract_code) {
		return null;
	}

	// 获取合约指数信息
	@RequestMapping(value = "/contract_index", method = RequestMethod.GET)
	public String getSecurityIndexInfo(@RequestParam(value = "symbol", required = false) String symbol) {
		return null;
	}

	// 获取合约最高限价和最低限价
	@RequestMapping(value = "/contract_price_limit", method = RequestMethod.GET)
	public String getSecurityPriceLimit(@RequestParam(value = "symbol", required = false) String symbol,
			@RequestParam(value = "contract_type", required = false) String contract_type,
			@RequestParam(value = "contract_code", required = false) String contract_code) {
		return null;
	}

	// 获取当前可用合约总持仓量
	@RequestMapping(value = "/contract_open_interest", method = RequestMethod.GET)
	public String getSecurityAllPosition(@RequestParam(value = "symbol", required = false) String symbol,
			@RequestParam(value = "contract_type", required = false) String contract_type,
			@RequestParam(value = "contract_code", required = false) String contract_code) {
		return null;
	}

	// 获取预估交割价
	@RequestMapping(value = "/contract_delivery_price", method = RequestMethod.GET)
	public String estimationDeliveryPrice(@RequestParam(value = "symbol", required = false) String symbol) {
		return null;
	}

}
