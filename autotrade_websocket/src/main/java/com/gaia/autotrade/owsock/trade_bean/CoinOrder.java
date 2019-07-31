package com.gaia.autotrade.owsock.trade_bean;

public class CoinOrder {
	public CoinOrder() {
	}

	// 是否修正
	public boolean m_bModify;
	// 经纪公司代码
	public String m_brokerID = "";
	// 撤销时间
	public String m_cancelTime = "";
	// 合约代码
	public String m_code = "";
	// 买卖方向
	public String m_direction = "";
	// 交易所代码
	public String m_exchangeID = "";
	// 交易日
	public String m_excutionDate = "";
	// 冻结手续费
	public double m_frozenCommission;
	// 冻结保证金
	public double m_frozenMargin;
	// 冻结手数
	public double m_frozenVolume;
	// 报单日期
	public String m_insertDate = "";
	// 委托时间
	public String m_insertTime = "";
	// 投资者代码
	public String m_investorID = "";
	// 价格
	public double m_limitPrice;
	// 报单价格条件
	public String m_orderPriceType = "";
	// 报单引用
	public String m_orderRef = "";
	// 报单状态
	public String m_orderStatus = "";
	// 报单编号
	public String m_orderSysID = "";
	// 报单类型
	public String m_orderType = "";
	// 请求编号
	public int m_requestID;
	// 状态信息
	public String m_statusMsg = "";
	// 交易所交易员代码
	public String m_traderID = "";
	// 交易日
	public String m_tradingDay = "";
	// 最后修改时间
	public String m_updateTime = "";
	// 剩余数量
	public double m_volumeTotal;
	// 今成交数量
	public double m_volumeTraded;

	/**
	 * 下面是周林的结构数据
	 */
	public int m_errorCode; // 错误代码 不错为0
	public String m_tradePair; // 交易对子
	public String m_orderID;// 订单id
	public String m_targetFee;// 手续费收取目标
	public String m_flag;// 客户端标识
	public String m_userID;// 用户ID
	public double m_price;// 价格
	public double m_volume;// 总手数
	public double m_orderFee;// 委托手续费
	public double m_timestamp;// 时间戳
	public int m_isFeeTartget;// 是否收取额外指定的手续费
	public int m_isDealFeeDemand;// 是否需要成交后收取手续费
	public int m_orderWay;// 委托类型
	public int m_dir;// 方向
	/**
	 * 上面是周林的结构数据
	 */

	/**
	 * 下面是盛俊杰定义的数据
	 */
	public double m_tradedVolume; // 已成交数量
	public double m_openedVolume; // 挂单数量
	public int m_orderState; // 委托状态

	/*
	 * 委托状态代码 #define ORDERSTATUS_P1 1//全部挂单 #define ORDERSTATUS_P2 2//全部撤单 #define
	 * ORDERSTATUS_P3 3//部分挂单, 部分撤单 #define ORDERSTATUS_P4 4//部分挂单, 部分成交 #define
	 * ORDERSTATUS_P5 5//部分挂单, 部分撤单, 部分成交 #define ORDERSTATUS_P6 6//完全成交
	 */
	/**
	 * 上面是盛俊杰定义的数据
	 */

	/**
	 * 返回主键
	 */
	public String GetKeyID() {
		return m_orderID;
	}

	/**
	 * 复制数据
	 *
	 * @param data 数据
	 */
	public final void Copy(CoinOrder data) {
		if (data == null) {
			return;
		}
		m_bModify = data.m_bModify;
		m_brokerID = data.m_brokerID;
		m_cancelTime = data.m_cancelTime;
		m_code = data.m_code;
		m_direction = data.m_direction;
		m_exchangeID = data.m_exchangeID;
		m_frozenCommission = data.m_frozenCommission;
		m_frozenMargin = data.m_frozenMargin;
		m_frozenVolume = data.m_frozenVolume;
		m_limitPrice = data.m_limitPrice;
		m_insertDate = data.m_insertDate;
		m_insertTime = data.m_insertTime;
		m_investorID = data.m_investorID;
		m_orderPriceType = data.m_orderPriceType;
		m_orderRef = data.m_orderRef;
		m_orderStatus = data.m_orderStatus;
		m_orderSysID = data.m_orderSysID;
		m_orderType = data.m_orderType;
		m_requestID = data.m_requestID;
		m_statusMsg = data.m_statusMsg;
		m_traderID = data.m_traderID;
		m_tradingDay = data.m_tradingDay;
		m_updateTime = data.m_updateTime;
		m_volumeTotal = data.m_volumeTotal;
		m_volumeTraded = data.m_volumeTraded;
	}

	/**
	 * 比较是否相同
	 *
	 * @param data 数据
	 * @return 是否相同
	 */
	public final boolean Equal(CoinOrder data) {
		if (data == null) {
			return false;
		}
		if (m_bModify == data.m_bModify && m_brokerID.equals(data.m_brokerID) && m_cancelTime.equals(data.m_cancelTime)
				&& m_code.equals(data.m_code) && m_direction.equals(data.m_direction)
				&& m_exchangeID.equals(data.m_exchangeID) && m_frozenCommission == data.m_frozenCommission
				&& m_frozenMargin == data.m_frozenMargin && m_frozenVolume == data.m_frozenVolume
				&& m_limitPrice == data.m_limitPrice && m_insertDate.equals(data.m_insertDate)
				&& m_insertTime.equals(data.m_insertTime) && m_investorID.equals(data.m_investorID)
				&& m_orderPriceType.equals(data.m_orderPriceType) && m_orderRef.equals(data.m_orderRef)
				&& m_orderStatus.equals(data.m_orderStatus) && m_orderSysID.equals(data.m_orderSysID)
				&& m_orderType.equals(data.m_orderType) && m_requestID == data.m_requestID
				&& m_statusMsg.equals(data.m_statusMsg) && m_traderID.equals(data.m_traderID)
				&& m_tradingDay.equals(data.m_tradingDay) && m_updateTime.equals(data.m_updateTime)
				&& m_volumeTotal == data.m_volumeTotal && m_volumeTraded == data.m_volumeTraded) {
			return true;
		}
		return false;
	}
}
