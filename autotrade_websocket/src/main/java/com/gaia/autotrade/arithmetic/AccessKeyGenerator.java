package com.gaia.autotrade.arithmetic;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccessKeyGenerator {

	/**
	 * MD5加密算法
	 * 
	 * @param content that needs to be encrypted
	 * @return encrypted content
	 */
	private static String md5(String content) {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 对字符串进行加密
			md.update(content.getBytes());
			// 获得加密后的数据
			byte[] secretBytes = md.digest();
			// 将加密后的数据转换为16进制数字
			String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
			// 如果生成数字未满32位，需要前面补0
			for (int i = 0; i < 32 - md5code.length(); i++) {
				md5code = "0" + md5code;
			}
			return md5code;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取SecretKey
	 * 
	 * @return SecretKey
	 */
	private static String getSecretKey(String accesskey) {
		String accessKey = "gaia" + accesskey;
		return md5(accessKey);
	}

	public static Map<String, String> getKeyPair() {
		HashMap<String, String> map = new HashMap<String, String>();
		String accesskey = UUID.randomUUID().toString();
		String secretkey = getSecretKey(accesskey);
		map.put("accesskey", accesskey);
		map.put("secretkey", secretkey);
		return map;
	}
}