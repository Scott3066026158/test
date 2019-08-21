package gaia.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author 盛俊杰
 * 此类主要是一些static方法,用于整个项目的简单的工具方法
 *
 */
public class PublicMethod {
	
	//截取指定字符串外剩余的字符串
	public static String subRemainString(String src, String match)
	{
		//判空
		if(IsEmpty(src, match))
		{
			return "";
		}
		//源字符串不包含匹配字符串
		if(!src.contains(match))
		{
			return "";
		}
		else
		{
			//获取匹配字符串在源字符串中的起始下标
			int startIndex = src.indexOf(match);
			//获取匹配字符串的长度
			int destLen = match.length();
			//获取结束下标
			int endIndex = startIndex + destLen;
			//截取
			String part1 = src.substring(0, startIndex);
			String part2 = src.substring(endIndex + 1, src.length());
			String subString = part1 + part2;
			return subString;
		}
		
	}
	/**
	 * 检测字符串是否存在""或者Null
	 * @param strings 需要检测的字符串
	 * @return 传入的所有字符串中若存在""或者Null,返回true,若没有,返回false
	 */
	public static boolean IsEmpty(String...strings)
	{
		for (String s : strings) {
			if (s == null || s.trim().equals("")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 按指定符号切割
	 * @param src 源字符串
	 * @param regex 切割符号
	 * @return <p>当传入的参数为""或者Null,返回Null</p>
	 *         <p>若参数正常,返回切割后的字符集合</p>
	 */
	public static ArrayList<String> Split(String src)
	{
		ArrayList<String> strList = Split(src, ",");
		return strList;
	}
	
	/**
	 * 按指定符号切割字符串,并以List集合返回
	 * @param src 源字符串
	 * @param regex 切割符号
	 * @return <p>当传入的参数为""或者Null,返回Null</p>
	 *         <p>若参数正常,返回切割后的字符集合</p>
	 */
	public static ArrayList<String> Split(String src, String regex)
	{
		if(PublicMethod.IsEmpty(src, regex)) 
		{
			return null;
		}
		else
		{
			ArrayList<String> splitResult = new ArrayList<String>();
			String[] destArray = src.split(regex);
			for(int i = 0; i < destArray.length; i++)
			{
				splitResult.add(destArray[i]);
			}
			return splitResult;
		}
	}
	
	/**
	 * 
	 * @param srcCollection 需要检测的集合
	 * @return <p>若srcCollection是null或者size是0,返回true</p>
	 */
	public static <T>boolean IsEmptyForCollection(Collection<T> srcCollection)
	{
		if(srcCollection == null)
			return true;
		if(srcCollection.size() == 0)
			return true;
		return false;
	}
}
