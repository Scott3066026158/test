package com.kunyi.bitamexJava.dao;

import com.kunyi.bitamexJava.model.UserInfoTable;

public interface UserRegisterService {

	/**
	 * 用户注册
	 * @param uTable
	 * @param registertype
	 * @return
	 */
	String userRegister(UserInfoTable uTable, String registertype);
	
}
