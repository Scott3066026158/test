package com.kunyi.bitamexJava.dao;

import java.util.List;

import com.kunyi.bitamexJava.model.RechargeInfoTable;

public interface RechargeInfoService {
	/**
	 * 查询充值信息
	 */
	List<RechargeInfoTable> queryRecharges(String phone, String realName, String idCardNo, String walletAddress, String coin, String startDate, String endDate);
	
	/**
	 * 添加充值记录
	 */
	int addRecharge(RechargeInfoTable rechargeInfo);
}
