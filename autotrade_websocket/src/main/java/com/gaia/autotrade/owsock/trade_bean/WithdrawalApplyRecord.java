package com.gaia.autotrade.owsock.trade_bean;

public class WithdrawalApplyRecord {
	public String m_flag; // requestID
	public String m_traderID;// 用户ID
	public int m_errorCode; // 申请成功,申请失败 1,0
	public String m_withdrawalID; // 出金ID
	public String m_code;// 币种
	public String m_outAddr;// 出金地址
	public String m_memo;// 出金备注
	public double m_balance;// 出金金额
	public double m_fee;// 出金手续费
	public String m_serialNumber; // 流水号
	public String m_withdrawalDate; // 提现时间(年月日)
	public String m_withdrawalTime; // 提现时间(时分秒)
	public String m_finishStatus; // 提现进度描述
	public double m_progress; // 提现进度百分比

	public void Copy(WithdrawalApplyRecord data) {
		if (data == null) {
			return;
		}
		m_flag = data.m_flag;
		m_traderID = data.m_traderID;
		m_errorCode = data.m_errorCode;
		m_withdrawalID = data.m_withdrawalID;
		m_code = data.m_code;
		m_outAddr = data.m_outAddr;
		m_memo = data.m_memo;
		m_balance = data.m_balance;
		m_fee = data.m_fee;
		m_serialNumber = data.m_serialNumber;
		m_withdrawalDate = data.m_withdrawalDate;
		m_withdrawalTime = data.m_withdrawalTime;
		m_finishStatus = data.m_finishStatus;
		m_progress = data.m_progress;
	}

	public boolean Equals(WithdrawalApplyRecord data) {
		if (m_serialNumber.equals(data.m_serialNumber)) {
			return true;
		} else {
			return false;
		}
	}
}
