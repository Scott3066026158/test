package com.kunyi.bitamexJava.dao;

import java.util.List;

import com.kunyi.bitamexJava.model.CoinInfoDetailTable;
import com.kunyi.bitamexJava.model.CoinInfoTable;

public interface CoinInfoService {

	/**
	 * 查询所有币种
	 */
	List<CoinInfoTable> queryCoins(String coin, String startDate, String endDate);
	
	/**
	 * 添加一个币种
	 */
	int addCoin(CoinInfoDetailTable coinInfoDetail);
	
	/**
	 * 更新币种提币状态
	 */
	int updateCoinStatus(String coinName, String status);
}
