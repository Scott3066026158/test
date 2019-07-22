package com.kunyi.bitamexJava.dao;

import java.util.List;

import com.kunyi.bitamexJava.model.WithdrawInfoTable;

public interface WithdrawInfoService {

	/**
	 * 查询出金记录
	 */
	List<WithdrawInfoTable> queryWithdrawals(String phone, String realName, String idCardNo, String walletAddress, String coin, String startDate, String endDate);
	
	/**
	 * 添加用户出金记录
	 */
	int addWithdrawal(WithdrawInfoTable withdrawInfo);
}
