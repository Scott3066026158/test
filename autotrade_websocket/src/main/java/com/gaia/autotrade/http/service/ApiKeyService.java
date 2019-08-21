package com.gaia.autotrade.http.service;

import org.springframework.stereotype.Service;

@Service
public interface ApiKeyService {

	/**
	 * create ApiKey
	 * 
	 * @param username
	 * @param remark
	 * @param privilege
	 * @param ipgroup
	 * @return
	 */
	String create(String username, String remark, String privilege, String ipgroup);

	/**
	 * get Apikey List
	 * 
	 * @param accesskey
	 * @return
	 */
	String getList(String accesskey);

	/**
	 * delete accesskey
	 * 
	 * @param accesskey
	 * @return
	 */
	String delete(String accesskey);
}
