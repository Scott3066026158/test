package com.kunyi.bitamexJava.dao;

public interface UserSelectService {

	/**
	 * 获取用户自选股信息
	 * @param traderId
	 * @return
	 */
	String getUserData(String traderId);

	/**
	 * 增加用户自选股信息
	 * @param traderId
	 * @param code
	 * @return
	 */
	String addUserData(String traderId, String code);

	/**
	 * 删除用户自选股信息
	 * @param traderId
	 * @param code
	 * @return
	 */
	String delUserData(String traderId, String code);

	
}
