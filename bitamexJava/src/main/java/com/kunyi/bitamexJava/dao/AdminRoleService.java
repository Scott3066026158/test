package com.kunyi.bitamexJava.dao;

import java.util.List;

import com.kunyi.bitamexJava.model.AdminRoleTable;

public interface AdminRoleService {
	/**
	 * 新增角色信息
	 * @param aRoleTable
	 * @return
	 */
	String addRole(AdminRoleTable aRoleTable);
	
	/**
	 * 根据条件查询角色信息
	 * @param name
	 * @param founder
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<AdminRoleTable> queryRoles(String name, String founder,String startTime,String endTime);
}
