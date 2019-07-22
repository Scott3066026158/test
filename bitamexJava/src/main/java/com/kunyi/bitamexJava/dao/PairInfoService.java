package com.kunyi.bitamexJava.dao;

import java.util.List;

import com.kunyi.bitamexJava.model.PairInfoDetailTable;
import com.kunyi.bitamexJava.model.PairInfoTable;

public interface PairInfoService {
	/**
	 * 查询交易对子
	 */
	List<PairInfoTable> queryPairs(String pair, String startDate, String endDate);
	
	/**
	 * 添加交易对子
	 */
	int addPair(PairInfoDetailTable pairInfoDetail, String username);
	
}
