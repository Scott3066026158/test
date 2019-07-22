package com.kunyi.bitamexJava.dao;

public interface UserLoginService {
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @param logintype
	 * @return
	 */
	String userLogin(String username,String password,String logintype);

	/**
	 * 开启或关闭登录时短信认证
	 * @param userid
	 * @param password
	 * @return
	 */
	String smsAuthened(String userid, String password);

	/**
	 * 获取谷歌秘钥
	 * @param userid
	 * @return
	 */
	String fetchgooglesecretkey(String userid);

	/**
	 * 开启或关闭登录时谷歌验证
	 * @param userid
	 * @param password
	 * @param googlecode
	 * @return
	 */
	String googleAuthened(Integer userid, String password, Integer googlecode);
	
	/**
	 * 验证谷歌验证码是否正确
	 * @param googlecode
	 * @param userid
	 * @return
	 */
	String checkGoogleCode(Integer googlecode, Integer userid);
	
}
