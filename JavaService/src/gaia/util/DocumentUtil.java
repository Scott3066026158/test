//GAIA_USERMANAGERSERVER_JAVA_S1:ZHOULIN100%
/********************************************************************************************\
*                                                                                           *
* DocumentUtil.java -         Object转Map,Map转Object                     *
*                                                                                           *
*               Version 1.00  �?                                                            *
*                                                                                           *
*               Copyright (c) 2016-2017, Gaia Financial technology. All rights reserved.    *
*               Created by Joel 2017/6/6.                                                  *
*                                                                                           *
*********************************************************************************************/
package gaia.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Map(Document)和Object互相转换的工�?
 * 
 * @author Joel
 *
 */
public class DocumentUtil
{
	/**
	 * 将Map转换成对�?
	 * <p>
	 * 这个方法还没有完�?
	 * </p>
	 * 
	 * @param type
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static <T> T Map2Object(Class<T> type, Map<String, Object> map) throws Exception
	{
		Set<String> keys = map.keySet();
		T result = type.newInstance();
		Field f = null;
		Field[] fields = type.getFields();
		for (String key : keys)
		{
			try
			{
				f = type.getDeclaredField(key);
			}
			catch (Exception e)
			{
				continue;
			}
			if (f != null)
			{
				Class<?> fieldType = f.getType();
				f.setAccessible(true);
				try
				{
					// Object obj = ;
					f.set(result, TypeTo(fieldType, map.get(key)));
				}
				catch (Exception e)
				{
					continue;
				}
			}
		}
		return result;
	}

	/**
	 * 将对象转换成Map
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> Object2Map(Object obj)
	{
		Map<String, Object> resultMAp = new HashMap<String, Object>();
		@SuppressWarnings("rawtypes")
		Class klass = obj.getClass();
		boolean includeSuperClass = klass.getClassLoader() != null;
		Field[] fields = includeSuperClass ? klass.getFields() : klass.getDeclaredFields();
		for (int i = 0; i < fields.length; i += 1)
		{
			try
			{
				Field field = fields[i];
				if (Modifier.isPublic(field.getModifiers()))
				{
					String name = field.getName();
					String key = "";
					if (name.startsWith("m_") || "_id".equals(name))
					{
						key = name;
					}
					if (key.length() > 0)
					{
						Object result = field.get(obj);
						if (result != null)
						{
							resultMAp.put(key, result);
						}
					}
				}
			}
			catch (Exception ignore)
			{
				ignore.printStackTrace();
				return null;
			}

		}
		return resultMAp;
	}

	/**
	 * 
	 * @param clzz
	 * @param obj
	 * @return
	 * @throws Exception
	 *             类型转换错误
	 */
	// TODO 此方法其他判断就�?要时添加
	private static Object TypeTo(Class<?> clzz, Object obj) throws Exception
	{
		if (clzz == String.class)
		{
			obj = obj.toString();
		}
		else if (Integer.class == clzz)
		{
			obj = Integer.valueOf(obj.toString());
		}
		else if (Long.class == clzz)
		{
			obj = Long.valueOf(obj.toString());
		}
		else if (Double.class == clzz)
		{
			obj = Double.valueOf(obj.toString());
		}
		else if (Float.class == clzz)
		{
			obj = Float.valueOf(obj.toString());
		}
		else if (Character.class == clzz)
		{
			// Character.valueOf(Integer.valueOf(obj.toString()));
		}
		else if (Date.class == clzz)
		{
			obj = new Date(Long.valueOf(obj.toString()));
		}
		else if (List.class == clzz)
		{
			//
		}
		else if (Map.class == clzz)
		{
			//
		}
		return obj;
	}
}
