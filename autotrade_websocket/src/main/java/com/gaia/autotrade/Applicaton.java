package com.gaia.autotrade;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSONObject;

public class Applicaton {
	
	//SpringContext对象
	private static ConfigurableApplicationContext m_context;
	//errorCode配置文件
	private static JSONObject m_errCodeMap = getErrorCode();

	public static ConfigurableApplicationContext getContext() {
		return m_context;
	}

	public static void setContext(ConfigurableApplicationContext context) {
		Applicaton.m_context = context;
	}
	
	public static JSONObject getErrorCode() {
		if(m_errCodeMap == null) {
			try {
				File jsonFile = ResourceUtils.getFile("classpath:errcode.json");
				String json = FileUtils.readFileToString(jsonFile);
				m_errCodeMap = JSONObject.parseObject(json);
		        return m_errCodeMap;
			} catch (Exception e) {
				System.out.println("配置文件解析出错!");
				return null;
			}
		}else {
			return m_errCodeMap;
		}
	}
	
}
