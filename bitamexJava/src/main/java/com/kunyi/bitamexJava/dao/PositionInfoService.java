package com.kunyi.bitamexJava.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kunyi.bitamexJava.model.PositionInfoTable;


public interface PositionInfoService {

	/**
	 * 查询资金
	 */
	List<PositionInfoTable> queryPositions(String phone, String realName, String idCardNo, String walletAddress);
	
	/**
	 * 添加用户资金
	 */
	int addPosition(PositionInfoTable positionInfo);
}
