package com.gaia.autotrade.http.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//账户接口
@CrossOrigin
@RestController
public class AccountController {

	// 币查询母账户下所有子账户资产信息
	@RequestMapping(value = "api/v1/contract_sub_account_list", method = RequestMethod.POST)
	public String queryAllChildAccountPositionInfo(@RequestParam(value = "symbol", required = false) String symbol) {
		return null;
	}

	// 查询单个子账户资产信息
	@RequestMapping(value = "api/v1/contract_sub_account_info", method = RequestMethod.POST)
	public String queryOneChildAccountPositionInfo(@RequestParam(value = "symbol", required = false) String symbol,
			@RequestParam(value = "sub_uid", required = true) Long sub_uid) {
		return null;
	}

	// 查询单个子账户持仓信息的
	@RequestMapping(value = "api/v1/contract_sub_position_info", method = RequestMethod.POST)
	public String queryChildAccountPositionInfo(@RequestParam(value = "symbol", required = false) String symbol,
			@RequestParam(value = "sub_uid", required = true) Long sub_uid) {
		return null;
	}

	// 查询用户财务记录
	@RequestMapping(value = "api/v1/contract_financial_record", method = RequestMethod.POST)
	public String queryFinancialRecord(@RequestParam(value = "symbol", required = true) String symbol,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "create_date", required = false) int create_date,
			@RequestParam(value = "page_index", required = false) int page_index,
			@RequestParam(value = "page_size", required = false) int page_size) {
		return null;
	}

	// 查询用户当前的下单量限制
	@RequestMapping(value = "api/v1/contract_order_limit", method = RequestMethod.POST)
	public String queryOrderLimit(@RequestParam(value = "symbol", required = false) String symbol,
			@RequestParam(value = "order_price_type", required = true) String order_price_type) {
		return null;
	}

	// 查询用户当前的手续费费率
	@RequestMapping(value = "api/v1/contract_fee", method = RequestMethod.POST)
	public String queryProcedureFeeRate(@RequestParam(value = "symbol", required = false) String symbol) {
		return null;
	}

	// 用户查询用户当前的划转限制
	@RequestMapping(value = "api/v1/contract_transfer_limit", method = RequestMethod.POST)
	public String queryUsertransferLimit(@RequestParam(value = "symbol", required = false) String symbol) {
		return null;
	}

	// 用户持仓量限制的查询
	@RequestMapping(value = "api/v1/contract_position_limit", method = RequestMethod.POST)
	public String queryPositionLimit(@RequestParam(value = "symbol", required = false) String symbol) {
		return null;
	}

	// 币币账户和合约账户间进行资金的划转
	@RequestMapping(value = "v1/futures/transfer", method = RequestMethod.POST)
	public String fundsTransfer(@RequestParam(value = "currency", required = true) String currency, // 资金划转
			@RequestParam(value = "amount", required = true) Double amount,
			@RequestParam(value = "type", required = true) String type) {
		return null;
	}

	// 闪电平仓下单
	@RequestMapping(value = "api/v1/lightning_close_position", method = RequestMethod.POST)
	public String lightningClosePositionOrder(@RequestParam(value = "symbol", required = false) String symbol,
			@RequestParam(value = "contract_type", required = false) String contract_type,
			@RequestParam(value = "contract_code", required = false) String contract_code,
			@RequestParam(value = "volume", required = true) Double volume,
			@RequestParam(value = "direction", required = true) String direction,
			@RequestParam(value = "client_order_id", required = false) Double client_order_id) {
		return null;
	}
}
