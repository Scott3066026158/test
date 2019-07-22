package com.gaia.autotrade.owsock.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import owchart.owlib.Base.*;

//****************************************************************************\
//*                                                                             *
//* CFileA.cs -    File functions, types, and definitions                        *
//*                                                                             *
//*               Version 1.00 ★★★★★                                       *
//*                                                                             *
//*               Copyright (c) 2016-2016, Client. All rights reserved.         *
//*               Created by Lord.                                              *
//*                                                                             *
//******************************************************************************


/** 
 文件操作类
 
*/
public class CFileA
{
	public static final int OF_READWRITE = 2;
	public static final int OF_SHARE_DENY_NONE = 0x40;

	/**
	 向文件中追加内容

	 @param file 文件
	 @param content 内容
	 @return 是否成功
	*/
	public static boolean Append(String file, String content)
	{
		try
		{
			OutputStream os = new FileOutputStream(new File(file), true);
			os.write(content.getBytes());
			os.close();
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 创建文件夹

	 @param dir 文件夹
	*/
	public static void CreateDirectory(String dir)
	{
		try
		{
			File f = new File(dir);
			if(f.exists())
			{
				f.mkdir();
			}
		}
		catch (Exception ex)
		{
		}
	}

	/**
	 获取文件夹中的文件夹

	 @param dir 文件夹
	 @param dirs 文件夹集合
	 @return
	*/
	public static boolean GetDirectories(String dir, java.util.ArrayList<String> dirs)
	{
		try
		{
			File f = new File(dir);
			if(f.exists() && f.isDirectory())
			{
				File[] fList = f.listFiles();
				int fListSize = fList.length;
				for(int i = 0; i < fListSize; i++)
				{
					File subFile = fList[i];
					if(subFile.isDirectory())
					{
						dirs.add(subFile.getAbsolutePath());
					}
				}
				return true;
			}
			return false;
		}
		catch (Exception ex)
		{
			return false;
		}
	}

	/**
	 获取文件夹中的文件

	 @param dir 文件夹
	 @param files 文件集合
	 @return 是否成功
	*/
	public static boolean GetFiles(String dir, java.util.ArrayList<String> files)
	{
		try
		{
			File f = new File(dir);
			if(f.exists() && f.isDirectory())
			{
				File[] fList = f.listFiles();
				int fListSize = fList.length;
				for(int i = 0; i < fListSize; i++)
				{
					File subFile = fList[i];
					if(subFile.isFile())
					{
						files.add(subFile.getAbsolutePath());
					}
				}
				return true;
			}
			return false;
		}
		catch (Exception ex)
		{
			return false;
		}
	}

	/**
	 判断文件夹是否存在

	 @param dir 文件夹
	 @return 是否存在
	*/
	public static boolean IsDirectoryExist(String dir)
	{
		try
		{
			File f = new File(dir);
			return f.isDirectory() && f.exists();
		}
		catch (Exception ex)
		{
			return false;
		}
	}

	/**
	 判断文件是否存在

	 @param file 文件
	 @return 是否存在
	*/
	public static boolean IsFileExist(String file)
	{
		try
		{
			File f = new File(file);
			return f.isFile() && f.exists();
		}
		catch (Exception ex)
		{
			return false;
		}
	}

	/**
	 从文件中读取内容

	 @param file 文件
	 @param content 返回内容
	 @return 是否成功
	*/
	public static boolean Read(String file, RefObject<String> content)
	{
		try
		{
			InputStream is = new FileInputStream(new File(file));
			byte[] buffer = new byte[is.available()];//新建一个字节数组
			is.read(buffer);
			content.argvalue = new String(buffer);
			is.close();
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 移除文件

	 @param file 文件
	 @return 是否成功
	*/
	public static boolean RemoveFile(String file)
	{
		return false;
	}

	/**
	 向文件中写入内容

	 @param file 文件
	 @param content 内容
	 @return 是否成功
	*/
	public static boolean Write(String file, String content)
	{
		try
		{
			OutputStream os = new FileOutputStream(new File(file));
			os.write(content.getBytes());
			os.close();
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
}