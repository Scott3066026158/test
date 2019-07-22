package com.kunyi.bitamexJava.dao;

import org.springframework.web.multipart.MultipartFile;

public interface UserUploadService {
	/**
	 * 储存用户上传的身份信息
	 * @param userID
	 * @param id
	 * @param name
	 * @return
	 */
	boolean saveUserInfo(String userID, String id, String name);

	/**
	 * 储存用户上传的身份证照片
	 * @param userID 
	 * @param imgFront
	 * @param imgBack
	 * @return
	 */
	boolean saveFile(String userID, MultipartFile imgFront, MultipartFile imgBack);

	/**
	 * 修改用户的审核状态信息
	 * @param userID
	 */
	boolean modifyUserStatus(String userID);	
}
