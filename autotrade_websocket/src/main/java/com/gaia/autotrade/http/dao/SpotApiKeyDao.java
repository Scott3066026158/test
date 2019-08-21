package com.gaia.autotrade.http.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.gaia.autotrade.arithmetic.AccessKeyGenerator;

@Repository
public class SpotApiKeyDao {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public String createApiKey(String username, String remark, String privilege, String ipgroup) {
		// 生成accesskey
		Map<String, String> map = AccessKeyGenerator.getKeyPair();
		// redisTemplate.opsForValue().set("ApiKey", "bpf");
		return null;
	}

	public String getApiKeyList(String access_key) {
		return null;
	}

	public String deleteApiKey(String access_key) {
		return null;
	}
}
