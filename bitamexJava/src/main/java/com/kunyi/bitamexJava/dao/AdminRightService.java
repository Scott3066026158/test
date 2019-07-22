package com.kunyi.bitamexJava.dao;

import java.util.List;

import com.kunyi.bitamexJava.model.AdminRightTable;

public interface AdminRightService {
	/**
	 * 新增管理员权限记录
	 * @param aRightTable
	 * @return
	 */
	boolean addRight(AdminRightTable aRightTable);
	
	/**
	 * 查询用户权限记录
	 * @param name
	 * @param founder
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<AdminRightTable> queryRight(String name, String founder,String startTime,String endTime);
	
}
