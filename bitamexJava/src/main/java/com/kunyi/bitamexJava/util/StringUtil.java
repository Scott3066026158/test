package com.kunyi.bitamexJava.util;

public class StringUtil {
	
	public static boolean isEmpty(String str){
		if(str == null || "".equals(str)){
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(Integer num){
		if(num == null || num == 0){
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(Long num){
		if(num == null || num == 0){
			return true;
		}
		return false;
	}
	
	public static boolean isTimeEmpty(String time){
		if(time == null || time.isEmpty() || "NaN".equals(time)){
			return true;
		}
		return false;
	}
}
