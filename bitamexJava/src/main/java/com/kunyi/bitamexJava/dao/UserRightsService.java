package com.kunyi.bitamexJava.dao;

public interface UserRightsService {

	/**
	 * 增加用户权益
	 * @param name
	 * @param founder
	 * @return
	 */
	String addRight(String name, String founder);

	/**
	 * 根据条件查询用户权限
	 * @param name
	 * @param founder
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	String queryRight(String name, String founder, String startTime,String endTime);

}
