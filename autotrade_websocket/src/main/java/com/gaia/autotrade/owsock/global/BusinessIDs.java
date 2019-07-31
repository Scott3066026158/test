package com.gaia.autotrade.owsock.global;

public class BusinessIDs {

	// 行情标识符
	public static final int QUOTE_LOGOUT = 32; // 行情登出请求
	public static final int REQ_QUOTE_LOGIN = 31; // 行情登录请求
	public static final int RSP_QUOTE_LOGIN = 131; // 行情登录回报
	public static final int REQ_QUOTE_CODES = 33; // 行情码表请求(纯交易对子)
	public static final int RSP_QUOTE_CODES = 133; // 行情码表回报(纯交易对子)
	public static final int REQ_QRY_INSTRUMENTINFO = 46;
	public static final int RSP_QRY_COININFO = 146;
	public static final int PUSH_DEPTH_DATA = 600;
	public static final int PUSH_TINY_DATA = 601;
	public static final int STOP_DEPTH_DATA = 602;
	public static final int STOP_TINY_DATA = 603;
	public static final int GET_HISTORY_DATA = 604;
	public static final int PUSH_KLINE_MINUTE = 605;

	// 交易标识符
	public static final int API_TD_LOGIN = 205; // 交易登录请求
	public static final int API_TD_LOGOUT = 204; // 交易登出请求
	public static final int API_TD_ORDER = 206; // 委托请求
	public static final int API_TD_CANCEL_ORDER = 207; // 撤单请求
	public static final int API_TD_POSITION = 208; // 资金推送
	public static final int API_TD_DEPOSIT = 209; // Ginex回报的充值地址
	public static final int API_TD_WITHDRAWAL = 210; // 出金请求
	public static final int QRY_RECHARGE_ADDR = 505; // 查询充值地址
	public static final int QRY_ALL_ORDERS = 500; // 查询所有委托
	public static final int QRY_ALL_TRADES = 501; // 查询所有成交
	public static final int QRY_ALL_WITHDRAWAL = 502; // 查询所有提现
	public static final int QRY_ALL_POSITIONS = 503; // 查询所有持仓
	public static final int API_TD_NOTIFY_ORDER_DEAL = 302; // 成交回报
	public static final int API_TD_NOTIFY_POSITION = 307; // 资金回报
	public static final int DIR_BUY = 66; // 委托方向买
	public static final int DIR_SELL = 83; // 委托方向卖

}
