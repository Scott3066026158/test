package com.kunyi.bitamexJava.dao;

public interface UserInfoService {
	/**
	 * 检查用户是否存在
	 * @param username
	 * @return
	 */
	String checkUserInfo(String username);
	
	/**
	 * 修改用户密码
	 * @param username
	 * @param newPassword
	 * @return
	 */
	String modifyUserPsaaword(String username,String newPassword);
	
	/**
	 * 邮箱用户绑定手机号
	 * @param userId
	 * @param phone
	 * @return
	 */
	String bindPhoneNumber(Integer userId, String phone);
	
	/**
	 * 解除绑定手机号
	 * @param userId
	 * @return
	 */
	String unBindPhoneNumber(Integer userId);
	
	/**
	 * 根据用户ID以及其邀请码获得用户所邀请的人数
	 * @param userId
	 * @param invitationCode
	 * @return
	 */
	String fetchNumberOfUserInvited(Integer userId, String invitationCode);
	
	/**
	 * 查询用户信息
	 * @param phone
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	String queryUserInfo(String phone,String name,String startTime,String endTime);
	
	/**
	 * 修改用户信息
	 * @param nickName
	 * @param phone
	 * @param password
	 * @param loginright
	 * @return
	 */
	String modifyUserInfo(String nickName,String area,String phone,String password,String loginright);
	
}
