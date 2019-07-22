package com.kunyi.bitamexJava.dao;

import java.util.List;

public interface UserRoleService {

	/**
	 * 新增用户角色
	 * @param name
	 * @param founder
	 * @param rights
	 * @return
	 */
	String addRole(String name, String founder, String rights);

	/**
	 * 根据条件查询用户角色
	 * @param name
	 * @param founder
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	String queryRole(String name, String founder, String startTime,String endTime);

}
