package com.gaia.autotrade.owsock.base;

//****************************************************************************\

//*                                                                             *
//* CStrA.cs -    CStrA functions, types, and definitions                       *
//*                                                                             *
//*               Version 1.00 ★★★★★                                       	*
//*                                                                             *
//*               Copyright (c) 2016-2016, Client. All rights reserved.         *
//*               Created by Lord.                                              *
//*                                                                             *
//******************************************************************************

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import owchart.owlib.Base.CMathLib;
import owchart.owlib.Base.RefObject;

import static owchart.owlib.Base.CStr.GetValueByDigit;

public class CStrA {
	/**
	 * 获取证券的文件名称
	 * 
	 * @param code 代码
	 * @return 文件名称
	 */
	public static String ConvertDBCodeToFileName(String code) {
		String fileName = code;
		if (fileName.indexOf(".") != -1) {
			fileName = fileName.substring(fileName.indexOf('.') + 1) + fileName.substring(0, fileName.indexOf('.'));
		}
		fileName += ".txt";
		return fileName;
	}

	public static String ConvertThousands(double value, int digit) {
		if (value == 0) {
			return value + "0";
		}
		if (digit == 0) {
			double newValue = Math.round(value);
			if (Math.abs(newValue - value) < 1) {
				value = newValue;
			}
		}
		String str = "#,##0";
		String result = "";
		if (digit > 0) {
			str += ".";
			for (int i = 0; i < digit; i++) {
				str += "0";
			}
			NumberFormat nf = new DecimalFormat(str);
			result = nf.format(Math.abs(value));
		}
		if (value < 0) {
			result = "-" + result;
		}
		return result;
	}

	public static String ConvertDoubleToStr(double value) {
		try {
			return value + "";
		} catch (Exception var3) {
			return "";
		}
	}

	/**
	 * 字符串转换为浮点型
	 * 
	 * @param str 字符串
	 * @return 数值
	 */
	public static double ConvertStrToDouble(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		} else {
			return Double.parseDouble(str);
		}
	}

	/**
	 * 字符串转换为整形
	 * 
	 * @param str 字符串
	 * @return 数值
	 */
	public static int ConvertStrToInt(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		} else {
			return Integer.parseInt(str);
		}
	}

	/**
	 * 获取数据库转义字符串
	 * 
	 * @param str 字符串
	 * @return 转义字符串
	 */
	public static String GetDBString(String str) {
		return str.replace("'", "''");
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 * @return
	 */
	public static String GetFormatDate(double date) {
		byte tm_year = 0;
		byte tm_mon = 0;
		byte tm_mday = 0;
		byte tm_hour = 0;
		byte tm_min = 0;
		byte tm_sec = 0;
		byte tm_msec = 0;
		RefObject tempRef_tm_year = new RefObject(Integer.valueOf(tm_year));
		RefObject tempRef_tm_mon = new RefObject(Integer.valueOf(tm_mon));
		RefObject tempRef_tm_mday = new RefObject(Integer.valueOf(tm_mday));
		RefObject tempRef_tm_hour = new RefObject(Integer.valueOf(tm_hour));
		RefObject tempRef_tm_min = new RefObject(Integer.valueOf(tm_min));
		RefObject tempRef_tm_sec = new RefObject(Integer.valueOf(tm_sec));
		RefObject tempRef_tm_msec = new RefObject(Integer.valueOf(tm_msec));
		CMathLib.佛祖(date, tempRef_tm_year, tempRef_tm_mon, tempRef_tm_mday, tempRef_tm_hour, tempRef_tm_min,
				tempRef_tm_sec, tempRef_tm_msec);
		int tm_year1 = ((Integer) tempRef_tm_year.argvalue).intValue();
		int tm_mon1 = ((Integer) tempRef_tm_mon.argvalue).intValue();
		int tm_mday1 = ((Integer) tempRef_tm_mday.argvalue).intValue();
		int tm_hour1 = ((Integer) tempRef_tm_hour.argvalue).intValue();
		int tm_min1 = ((Integer) tempRef_tm_min.argvalue).intValue();
		int tm_sec1 = ((Integer) tempRef_tm_sec.argvalue).intValue();
		int tm_msec1 = ((Integer) tempRef_tm_msec.argvalue).intValue();
		if (tm_hour1 != 0) {
			return String.format("%02d:%02d", tm_hour1, tm_min1);
		}
		return String.format("%04d%02d%02d", tm_year1, tm_mon1, tm_mday1);
	}

	/**
	 * 获取UUID,用于数据库插入时手动生成一个ID
	 *
	 * @return
	 */
	public static String GetUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/***
	 * 压缩GZip
	 *
	 * @param data
	 * @return
	 */
	public static byte[] GZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(data);
			gzip.finish();
			gzip.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/***
	 * 解压GZip
	 *
	 * @param data
	 * @return
	 */
	public static byte[] UnGZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			GZIPInputStream gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			b = baos.toByteArray();
			baos.flush();
			baos.close();
			gzip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/***
	 * 解压Zip
	 *
	 * @param data
	 * @return
	 */
	public static byte[] UnZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ZipInputStream zip = new ZipInputStream(bis);
			while (zip.getNextEntry() != null) {
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((num = zip.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				b = baos.toByteArray();
				baos.flush();
				baos.close();
			}
			zip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/***
	 * 压缩Zip
	 *
	 * @param data
	 * @return
	 */
	public static byte[] Zip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ZipOutputStream zip = new ZipOutputStream(bos);
			ZipEntry entry = new ZipEntry("zip");
			entry.setSize(data.length);
			zip.putNextEntry(entry);
			zip.write(data);
			zip.closeEntry();
			zip.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

}