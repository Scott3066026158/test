package com.gaia.autotrade.http.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//交易接口
@RestController
@RequestMapping(value = "api/v1")
public class TransactionController {

	// 合约下单
	@RequestMapping(value = "/contract_order", method = RequestMethod.POST)
	public String contractOrders(@RequestParam(value = "symbol", required = true) String symbol,
			@RequestParam(value = "contract_type", required = true) String contract_type,
			@RequestParam(value = "contract_code", required = true) String contract_code,
			@RequestParam(value = "client_order_id", required = false) Long client_order_id,
			@RequestParam(value = "price", required = true) Double price,
			@RequestParam(value = "volume", required = true) Long volume,
			@RequestParam(value = "direction", required = true) String direction,
			@RequestParam(value = "offset", required = true) String offset,
			@RequestParam(value = "lever_rate", required = true) int lever_rate,
			@RequestParam(value = "order_price_type", required = true) String order_price_type) {

		return null;
	}

	// 合约批量下单*********<list> (属性名称：orders_data)
	@RequestMapping(value = "/contract_batchorder", method = RequestMethod.POST)
	public String contractBatchOrders(@RequestParam(value = "symbol", required = false) String symbol,
			@RequestParam(value = "contract_type", required = false) String contract_type,
			@RequestParam(value = "contract_code", required = false) String contract_code,
			@RequestParam(value = "client_order_id", required = false) Long client_order_id,
			@RequestParam(value = "price", required = true) Double price,
			@RequestParam(value = "volume", required = true) Long volume,
			@RequestParam(value = "direction", required = true) String direction,
			@RequestParam(value = "offset", required = true) String offset,
			@RequestParam(value = "lever_rate", required = true) int lever_rate,
			@RequestParam(value = "order_price_type", required = true) String order_price_type) {

		return null;
	}

	// 撤销订单
	@RequestMapping(value = "/contract_cancel", method = RequestMethod.POST)
	public String OrderCancel(@RequestParam(value = "order_id", required = false) String order_id,
			@RequestParam(value = "client_order_id", required = false) String client_order_id,
			@RequestParam(value = "symbol", required = false) String symbol) {

		return null;
	}

	// 全部撤销
	@RequestMapping(value = "/contract_cancelall", method = RequestMethod.POST)
	public String OrderAllCancel(@RequestParam(value = "symbol", required = true) String symbol,
			@RequestParam(value = "contract_code", required = false) String contract_code,
			@RequestParam(value = "contract_type", required = false) String contract_type) {

		return null;
	}

	// 获取合约订单信息
	@RequestMapping(value = "/contract_order_info", method = RequestMethod.POST)
	public String getContractOrderInfo(@RequestParam(value = "order_id", required = false) String order_id,
			@RequestParam(value = "client_order_id", required = false) String client_order_id,
			@RequestParam(value = "symbol", required = true) String symbol) {

		return null;
	}

	// 获取订单明细信息
	@RequestMapping(value = "/contract_order_detail", method = RequestMethod.POST)
	public String getOrderDetailInfo(@RequestParam(value = "symbol", required = true) String symbol,
			@RequestParam(value = "order_id", required = true) Long order_id,
			@RequestParam(value = "created_at", required = true) Long created_at,
			@RequestParam(value = "order_type", required = true) int order_type,
			@RequestParam(value = "page_index", required = false) int page_index,
			@RequestParam(value = "page_size", required = false) int page_size) {

		return null;
	}

	// 获取合约当前未成交委托
	@RequestMapping(value = "/contract_openorders", method = RequestMethod.POST)
	public String getContractOpenOrdersEntrust(@RequestParam(value = "symbol", required = true) String symbol,
			@RequestParam(value = "page_index", required = false) int page_index,
			@RequestParam(value = "page_size", required = false) int page_size) {

		return null;
	}

	// 获取合约历史委托
	@RequestMapping(value = "/contract_hisorders", method = RequestMethod.POST)
	public String getContractHistoricalEntrust(@RequestParam(value = "symbol", required = true) String symbol,
			@RequestParam(value = "trade_type", required = true) int trade_type,
			@RequestParam(value = "type", required = true) int type,
			@RequestParam(value = "status", required = true) int status,
			@RequestParam(value = "create_date", required = true) int create_date,
			@RequestParam(value = "page_index", required = false) int page_index,
			@RequestParam(value = "page_size", required = false) int page_size) {

		return null;
	}

	// 获取历史成交记录
	@RequestMapping(value = "/contract_matchresults", method = RequestMethod.POST)
	public String getHistoricalTransactionRecord(@RequestParam(value = "symbol", required = true) String symbol,
			@RequestParam(value = "trade_type", required = true) int trade_type,
			@RequestParam(value = "create_date", required = true) int create_date,
			@RequestParam(value = "contract_code", required = true) String contract_code,
			@RequestParam(value = "page_index", required = false) int page_index,
			@RequestParam(value = "page_size", required = false) int page_size) {

		return null;
	}

}
