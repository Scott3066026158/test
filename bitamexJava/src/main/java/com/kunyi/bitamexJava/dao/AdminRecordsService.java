package com.kunyi.bitamexJava.dao;

import java.util.List;

import com.kunyi.bitamexJava.model.AdminRecordsTable;

public interface AdminRecordsService {
	
	/**
	 * 新增一条管理员操作记录
	 * @param aRecordsTable
	 * @return
	 */
	boolean addAdminRecords(AdminRecordsTable aRecordsTable);
	
	/**
	 * 查询管理员操作记录表
	 * @param name
	 * @param roleID
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<AdminRecordsTable> queryAdminRecords(String name,Integer roleID,String startTime,String endTime);
}
