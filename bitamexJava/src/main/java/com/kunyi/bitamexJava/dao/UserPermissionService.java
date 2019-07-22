package com.kunyi.bitamexJava.dao;

public interface UserPermissionService {

	/**
	 * 获取用户权限
	 * @param userID
	 * @return
	 */
	String getUserPermission(Integer userID);

	/**
	 * 设置用户权限
	 * @param userID
	 * @param userpermission
	 * @return
	 */
	String setUserPermission(Integer userID, Integer userpermission);

	/**
	 * 审核用户
	 * @param name
	 * @param result
	 * @return
	 */
	String auditUser(String name, String result);

}
