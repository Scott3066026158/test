package com.gaia.autotrade.http.spot_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpotApiKeyService {
	
	public String createApiKey(String username, String remark, String privilege, String ipgroup) {
		
		return null;
	}
	
	public String getApiKeyList(String access_key) {
		return null;
	}
	
	public String deleteApiKey(String access_key) {
		return null;
	}
}
