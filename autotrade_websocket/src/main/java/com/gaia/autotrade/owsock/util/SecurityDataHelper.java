package com.gaia.autotrade.owsock.util;

import java.util.ArrayList;

import com.gaia.autotrade.owsock.bean.Indicator;
import com.gaia.autotrade.owsock.bean.IndicatorData;
import com.gaia.autotrade.owsock.market_bean.KeyFields;
import com.gaia.autotrade.owsock.market_bean.MarketKLineData;
import com.gaia.autotrade.owsock.market_bean.MinuteKLineParam;

import owchart.owlib.Base.CMathLib;
import owchart.owlib.Base.RefObject;
import owchart.owlib.Chart.CIndicator;
import owchart.owlib.Chart.CTable;
import owchart.owlib.Chart.CTableEx;
import owchart.owlib.Chart.ChartA;
import owchart.owlib.Indicator.CIndicatorEx;

//****************************************************************************\
//*                                                                             *
//* SecurityDataHelper.cs - Security data functions, types, and definitions.    *
//*                                                                             *
//*               Version 1.00  ★★★                                          *
//*                                                                             *
//*               Copyright (c) 2016-2016, Piratecat. All rights reserved.      *
//*               Created by Lord 2016/1/30.                                    *
//*                                                                             *
//*****************************************************************************

/**
 * 股票数据处理
 * 
 */
public class SecurityDataHelper {
	/**
	 * 绑定历史数据
	 * 
	 * @param chart        股票控件
	 * @param dataSource   数据源
	 * @param indicators   指标
	 * @param fields       字段
	 * @param historyDatas 历史数据
	 */
	public static void BindHistoryDatas(ChartA chart, CTable dataSource, java.util.ArrayList<CIndicator> indicators,
			int[] fields, java.util.ArrayList<MarketKLineData> historyDatas, boolean showMinuteLine) {
		dataSource.Clear();
		int sizeData = historyDatas.size();
		if (showMinuteLine) {
			MarketKLineData securityData = historyDatas.get(0);
			double date = securityData.m_date;
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
			int tm_mHour1 = ((Integer) tempRef_tm_hour.argvalue).intValue();
			if (tm_mHour1 <= 6) {
				date = date - 60 * 60 * 24;
				CMathLib.佛祖(date, tempRef_tm_year, tempRef_tm_mon, tempRef_tm_mday, tempRef_tm_hour, tempRef_tm_min,
						tempRef_tm_sec, tempRef_tm_msec);
				tm_year1 = ((Integer) tempRef_tm_year.argvalue).intValue();
				tm_mon1 = ((Integer) tempRef_tm_mon.argvalue).intValue();
				tm_mday1 = ((Integer) tempRef_tm_mday.argvalue).intValue();
			}
			double nextDate = date + 60 * 60 * 24;
			byte tm_year_nextday = 0;
			byte tm_mon_nextday = 0;
			byte tm_mday_nextday = 0;
			byte tm_hour_nextday = 0;
			byte tm_min_nextday = 0;
			byte tm_sec_nextday = 0;
			byte tm_msec_nextday = 0;
			RefObject tempRef_tm_year_nextday = new RefObject(Integer.valueOf(tm_year_nextday));
			RefObject tempRef_tm_mon_nextday = new RefObject(Integer.valueOf(tm_mon_nextday));
			RefObject tempRef_tm_mday_nextday = new RefObject(Integer.valueOf(tm_mday_nextday));
			RefObject tempRef_tm_hour_nextday = new RefObject(Integer.valueOf(tm_hour_nextday));
			RefObject tempRef_tm_min_nextday = new RefObject(Integer.valueOf(tm_min_nextday));
			RefObject tempRef_tm_sec_nextday = new RefObject(Integer.valueOf(tm_sec_nextday));
			RefObject tempRef_tm_msec_nextday = new RefObject(Integer.valueOf(tm_msec_nextday));
			CMathLib.佛祖(nextDate, tempRef_tm_year_nextday, tempRef_tm_mon_nextday, tempRef_tm_mday_nextday,
					tempRef_tm_hour_nextday, tempRef_tm_min_nextday, tempRef_tm_sec_nextday, tempRef_tm_msec_nextday);
			int tm_year1_nextday = ((Integer) tempRef_tm_year_nextday.argvalue).intValue();
			int tm_mon1_nextday = ((Integer) tempRef_tm_mon_nextday.argvalue).intValue();
			int tm_mday1_nextday = ((Integer) tempRef_tm_mday_nextday.argvalue).intValue();
			long minTime = (long) CMathLib.耶稣(tm_year1, tm_mon1, tm_mday1, 7, 0, 0, 0);
			long maxTime = (long) CMathLib.耶稣(tm_year1_nextday, tm_mon1_nextday, tm_mday1_nextday, 6, 0, 0, 0);
			int size = (int) ((maxTime - minTime) / 60);
			dataSource.SetRowsCapacity(size + 10);
			dataSource.SetRowsGrowStep(100);
			for (long dateD = minTime; dateD <= maxTime; dateD = dateD + 60) {
				dataSource.Set(dateD, fields[4], Double.NaN);
				int indexDate = dataSource.GetRowIndex(dateD);
				if (indexDate != -1) {
					dataSource.Set2(indexDate, fields[0], Double.NaN);
					dataSource.Set2(indexDate, fields[1], Double.NaN);
					dataSource.Set2(indexDate, fields[2], Double.NaN);
					dataSource.Set2(indexDate, fields[3], Double.NaN);
					dataSource.Set2(indexDate, fields[5], Double.NaN);
					dataSource.Set2(indexDate, fields[6], Double.NaN);
					dataSource.Set2(indexDate, fields[7], Double.NaN);
				}
			}
		} else {
			dataSource.SetRowsCapacity(sizeData + 10);
			dataSource.SetRowsGrowStep(100);
		}

		int size = historyDatas.size();
		int columnsCount = dataSource.GetColumnsCount();
		for (int i = 0; i < size; i++) {
			MarketKLineData securityData = historyDatas.get(i);
			if (dataSource == chart.GetDataSource()) {
				if (securityData.m_close > 0) {
					InsertData(chart, dataSource, fields, securityData);
				}

			} else {
				double[] ary = new double[columnsCount];
				ary[0] = securityData.m_close;
				ary[1] = securityData.m_high;
				ary[2] = securityData.m_low;
				ary[3] = securityData.m_open;
				ary[4] = securityData.m_volume;
				ary[5] = securityData.m_amount;
				ary[6] = securityData.m_avgPrice;
				ary[7] = securityData.m_settlePrice;
//                for (int j = 5; j < columnsCount; j++)
//                {
//                    ary[j] = Double.NaN;
//                }
				dataSource.AddRow(securityData.m_date, ary, columnsCount);
			}
		}
		int indicatorsSize = indicators.size();
		for (int i = 0; i < indicatorsSize; i++) {
			indicators.get(i).OnCalculate(0);
		}
	}

	public static void BindHistoryVolAndAmountDatas(CTable dataSource, int[] fields,
			ArrayList<MarketKLineData> historyDatas) {
		dataSource.Clear();
		int size = (int) historyDatas.size();
		dataSource.SetRowsCapacity(size + 10);
		dataSource.SetRowsGrowStep(100);
		int columnsCount = dataSource.GetColumnsCount();
		for (int i = 0; i < size; i++) {
			MarketKLineData securityData = historyDatas.get(i);
			double[] ary = new double[columnsCount];
			ary[0] = securityData.m_volume;
			ary[1] = securityData.m_amount;
			dataSource.AddRow((double) securityData.m_date, ary, columnsCount);
		}
	}

	/**
	 * 计算分钟线
	 * 
	 * @param minuteDate
	 * @param type
	 * @param cycle
	 * @return
	 */
	public static int CalculateMinuteKLineDate(MinuteKLineParam minuteDate, int type, int cycle) {
		if (cycle < KeyFields.CYCLE_MINUTE_1 || cycle > KeyFields.CYCLE_MINUTE_60) {
			return 0;
		}

		int realCount = GetRealPeriodCount(cycle);
		minuteDate.m_hour_cycle = minuteDate.m_hour;
		minuteDate.m_minute_cycle = minuteDate.m_minute / realCount;

		return 1;
	}

	/**
	 * 添加指标
	 * 
	 * @param chart      股票控件
	 * @param dataSource 数据源
	 * @param text       文本
	 * @param parameters 参数
	 */
	public static CIndicator CreateIndicator(ChartA chart, CTable dataSource, String text, String parameters) {
		CIndicator indicator = new CIndicatorEx();
		indicator.SetDataSource(dataSource);
		indicator.SetName("");
		if (dataSource != null) {
			indicator.SetSourceField(KeyFields.CLOSE, KeyFields.CLOSE_INDEX);
			indicator.SetSourceField(KeyFields.HIGH, KeyFields.HIGH_INDEX);
			indicator.SetSourceField(KeyFields.LOW, KeyFields.LOW_INDEX);
			indicator.SetSourceField(KeyFields.OPEN, KeyFields.OPEN_INDEX);
			indicator.SetSourceField(KeyFields.VOL, KeyFields.VOL_INDEX);
			indicator.SetSourceField(KeyFields.AMOUNT, KeyFields.AMOUNT_INDEX);
			indicator.SetSourceField(KeyFields.CLOSE.substring(0, 1), KeyFields.CLOSE_INDEX);
			indicator.SetSourceField(KeyFields.HIGH.substring(0, 1), KeyFields.HIGH_INDEX);
			indicator.SetSourceField(KeyFields.LOW.substring(0, 1), KeyFields.LOW_INDEX);
			indicator.SetSourceField(KeyFields.OPEN.substring(0, 1), KeyFields.OPEN_INDEX);
			indicator.SetSourceField(KeyFields.VOL.substring(0, 1), KeyFields.VOL_INDEX);
			indicator.SetSourceField(KeyFields.AMOUNT.substring(0, 1), KeyFields.AMOUNT_INDEX);
		}
		IndicatorData indicatorData = new IndicatorData();
		indicatorData.m_parameters = parameters;
		indicatorData.m_script = text;
		indicator.SetTag(indicatorData);
		String constValue = "";
		if ((int) parameters.length() > 0) {
			String[] strs = parameters.split("[;]");
			int strsSize = (int) strs.length;
			for (int i = 0; i < strsSize; i++) {
				String str = strs[i];
				String[] strs2 = str.split("[,]");
				constValue += "const " + strs2[0] + ":" + strs2[3] + ";";
			}
		}
		if ((int) text.length() > 0) {
			indicator.SetScript(constValue + text);
		}
		return indicator;
	}

	/**
	 * 创建数据源
	 * 
	 * @param chart 股票控件
	 * @return 数据源
	 */
	public static CTable CreateDataSource(ChartA chart) {
		CTable dataSource = new CTableEx();
		dataSource.AddColumn(KeyFields.CLOSE_INDEX);
		dataSource.AddColumn(KeyFields.HIGH_INDEX);
		dataSource.AddColumn(KeyFields.LOW_INDEX);
		dataSource.AddColumn(KeyFields.OPEN_INDEX);
		dataSource.AddColumn(KeyFields.VOL_INDEX);
		dataSource.AddColumn(KeyFields.AMOUNT_INDEX);
		return dataSource;
	}

	/**
	 * 获取周几
	 * 
	 * @param y
	 * @param m
	 * @param d
	 * @return
	 */
	public static int DayOfWeek(int y, int m, int d) {
		if (m == 1 || m == 2) {
			m += 12;
			y--;
		}
		return (d + 2 * m + 3 * (m + 1) / 5 + y + y / 4 - y / 100 + y / 400) % 7;
	}

	public static void GetIndicatorByName(String name, Indicator indicator) {
		if (name.equals("MA")) {
			indicator.m_name = "MA";
			indicator.m_description = "移动平均线";
			indicator.m_parameters = "N1,0,1000,5;N2,0,1000,10;N3,0,1000,20;";
			indicator.m_text = "MA5:MA(CLOSE,N1);MA10:MA(CLOSE,N2);MA20:MA(CLOSE,N3);";
		} else if (name.equals("MACD")) {
			indicator.m_name = "MACD";
			indicator.m_description = "指数平滑异同平均线";
			indicator.m_parameters = "SHORT,0,1000,12;LONG,0,1000,26;MID,0,1000,9;";
			indicator.m_text = "DIF:EMA(CLOSE,SHORT)-EMA(CLOSE,LONG);DEA:EMA(DIF,MID);MACD:(DIF-DEA)*2,COLORSTICK;";
		} else if (name.equals("KDJ")) {
			indicator.m_name = "KDJ";
			indicator.m_description = "随机指标";
			indicator.m_parameters = "N,0,1000,9;M1,0,1000,3;M2,0,1000,3;";
			indicator.m_text = "RSV:=(CLOSE-LLV(LOW,N))/(HHV(HIGH,N)-LLV(LOW,N))*100;K:SMA(RSV,M1,1);D:SMA(K,M2,1);J:3*K-2*D;";
		} else if (name.equals("RSI")) {
			indicator.m_name = "RSI";
			indicator.m_description = "相对强弱指标";
			indicator.m_parameters = "N1,0,1000,6;N2,0,1000,12;N3,0,1000,24;";
			indicator.m_text = "LC:=REF(CLOSE,1);RSI1:SMA(MAX(CLOSE-LC,0),N1,1)/SMA(ABS(CLOSE-LC),N1,1)*100;RSI2:SMA(MAX(CLOSE-LC,0),N2,1)/SMA(ABS(CLOSE-LC),N2,1)*100;RSI3:SMA(MAX(CLOSE-LC,0),N3,1)/SMA(ABS(CLOSE-LC),N3,1)*100;";
		} else if (name.equals("ROC")) {
			indicator.m_name = "ROC";
			indicator.m_description = "变动率指标";
			indicator.m_parameters = "N,0,1000,12;M,0,1000,6;";
			indicator.m_text = "ROC:100*(CLOSE-REF(CLOSE,N))/REF(CLOSE,N);MAROC:MA(ROC,M);";
		} else if (name.equals("BIAS")) {
			indicator.m_name = "BIAS";
			indicator.m_description = "乖离率";
			indicator.m_parameters = "N1,0,100,6;N2,0,100,12;N3,0,100,24;M4,0,1000,60;";
			indicator.m_text = "BIAS1:(CLOSE-MA(CLOSE,N1))/MA(CLOSE,N1)*100; BIAS2:(CLOSE-MA(CLOSE,N2))/MA(CLOSE,N2)*100;BIAS3:(CLOSE-MA(CLOSE,N3))/MA(CLOSE,N3)*100;";
		} else if (name.equals("CCI")) {
			indicator.m_name = "CCI";
			indicator.m_description = "商品路径指标";
			indicator.m_parameters = "N,0,100,14;M,0,1000,9;";
			indicator.m_text = "TYP:=(HIGH+LOW+CLOSE)/3;CCI:(TYP-MA(TYP,N))/(0.015*AVEDEV(TYP,N));";
		} else if (name.equals("WR")) {
			indicator.m_name = "WR";
			indicator.m_description = "威廉指标";
			indicator.m_parameters = "N1,0,1000,5;N2,0,1000,10;";
			indicator.m_text = "WR1:100*(HHV(HIGH,N1)-CLOSE)/(HHV(HIGH,N1)-LLV(LOW,N1));WR2:100*(HHV(HIGH,N2)-CLOSE)/(HHV(HIGH,N2)-LLV(LOW,N2));";
		} else if (name.equals("BOLL")) {
			indicator.m_name = "BOLL";
			indicator.m_description = "布林带";
			indicator.m_parameters = "N,0,100,20;";
			indicator.m_text = "BOLL:MA(CLOSE,N);UB:BOLL+2*STD(CLOSE,N);LB:BOLL-2*STD(CLOSE,N);";
		} else if (name.equals("BBI")) {
			indicator.m_name = "BBI";
			indicator.m_description = "多空指标";
			indicator.m_parameters = "N1,0,100,3;N2,0,100,6;N3,0,100,12;N4,0,100,24;";
			indicator.m_text = "BBI:(MA(CLOSE,N1)+MA(CLOSE,N2)+MA(CLOSE,N3)+MA(CLOSE,N4))/4;";
		} else if (name.equals("TRIX")) {
			indicator.m_name = "TRIX";
			indicator.m_description = "三重指数平均线";
			indicator.m_parameters = "N,0,1000,12;M,0,1000,9;";
			indicator.m_text = "MTR:=EMA(EMA(EMA(CLOSE,N),N),N);TRIX:(MTR-REF(MTR,1))/REF(MTR,1)*100;MATRIX:MA(TRIX,M);";
		} else if (name.equals("DMA")) {
			indicator.m_name = "DMA";
			indicator.m_description = "平均差";
			indicator.m_parameters = "N1,0,1000,10;N2,0,10000,50;";
			indicator.m_text = "DIF:MA(CLOSE,N1)-MA(CLOSE,N2); DIFMA:MA(DIF,N1);";
		}
	}

	/**
	 * 计算周期
	 * 
	 * @param cycle
	 * @return
	 */
	public static int GetRealPeriodCount(int cycle) {
		int sCount = 0;
		switch (cycle) {
		case KeyFields.CYCLE_MINUTE_1:
			sCount = 1;
			break;
		case KeyFields.CYCLE_MINUTE_3:
			sCount = 3;
			break;
		case KeyFields.CYCLE_MINUTE_5:
			sCount = 5;
			break;
		case KeyFields.CYCLE_MINUTE_10:
			sCount = 10;
			break;
		case KeyFields.CYCLE_MINUTE_15:
			sCount = 15;
			break;
		case KeyFields.CYCLE_MINUTE_30:
			sCount = 30;
			break;
		case KeyFields.CYCLE_MINUTE_60:
			sCount = 60;
			break;
		default:
			sCount = 0;
			break;
		}
		return sCount;
	}

	/**
	 * 插入数据
	 * 
	 * @param chart        证券控件
	 * @param dataSource   数据源
	 * @param fields       字段
	 * @param securityData 证券数据
	 * @return 索引
	 */
	public static int InsertData(ChartA chart, CTable dataSource, int[] fields, MarketKLineData securityData) {
		double close = securityData.m_close, high = securityData.m_high, low = securityData.m_low,
				open = securityData.m_open, avgPrice = securityData.m_avgPrice, volume = securityData.m_volume,
				amount = securityData.m_amount, settlementPrice = securityData.m_settlePrice;
		;
		if (volume > 0 || close > 0) {
			if (high == 0) {
				high = close;
			}
			if (low == 0) {
				low = close;
			}
			if (open == 0) {
				open = close;
			}
			if (avgPrice == 0) {
				avgPrice = Double.NaN;
			}
		} else {
			close = Double.NaN;
			high = Double.NaN;
			low = Double.NaN;
			open = Double.NaN;
			volume = Double.NaN;
			amount = Double.NaN;
			avgPrice = Double.NaN;
			settlementPrice = 0;
		}
		double date = securityData.m_date;
		dataSource.Set(date, fields[4], volume);
		int index = dataSource.GetRowIndex(date);
		dataSource.Set2(index, fields[0], close);
		dataSource.Set2(index, fields[1], high);
		dataSource.Set2(index, fields[2], low);
		dataSource.Set2(index, fields[3], open);
		dataSource.Set2(index, fields[5], amount);
		dataSource.Set2(index, fields[6], avgPrice);
		dataSource.Set2(index, fields[7], settlementPrice);
		return index;
	}

	/**
	 * 插入最新数据
	 * 
	 * @param chart
	 * @param dataSource
	 * @param indicators
	 * @param fields
	 * @param preClose
	 * @param index
	 */
	public static void InsertLatestData(CTable dataSource, ArrayList<CIndicator> indicators, int[] fields,
			float preClose, int index) {
		for (int i = 0; i < index; i++) {
			double close = dataSource.Get2(i, fields[0]);
			if (Double.isNaN(close)) {
				dataSource.Set2(i, fields[0], preClose);
				dataSource.Set2(i, fields[1], preClose);
				dataSource.Set2(i, fields[2], preClose);
				dataSource.Set2(i, fields[3], preClose);
				dataSource.Set2(i, fields[4], 0);
				dataSource.Set2(i, fields[5], 0);
				dataSource.Set2(i, fields[6], 0);
				dataSource.Set2(i, fields[7], preClose);
			} else {
				preClose = (float) close;
			}
		}
	}

	/**
	 * 插入最新数据
	 * 
	 * @param chart      股票控件
	 * @param dataSource 数据源
	 * @param indicators 指标
	 * @param fields     字段
	 * @param latestData 实时数据
	 * @return 索引
	 */
	public static int InsertLatestData(ChartA chart, CTable dataSource, java.util.ArrayList<CIndicator> indicators,
			int[] fields, MarketKLineData latestData) {
		if (latestData.m_close > 0 && latestData.m_volume > 0) {
			int indicatorsSize = indicators.size();
			int index = InsertData(chart, dataSource, fields, latestData);
			for (int i = 0; i < indicatorsSize; i++) {
				indicators.get(i).OnCalculate(index);
			}
			return index;
		} else {
			return -1;
		}
	}

	public static double SumHistoryData(CTable dataSource, double date, int cycle, int field) {
		double sumValue = 0;
		double value = 0;
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
		int year = ((Integer) tempRef_tm_year.argvalue).intValue();
		int month = ((Integer) tempRef_tm_mon.argvalue).intValue();
		int day = ((Integer) tempRef_tm_mday.argvalue).intValue();
		int hour = ((Integer) tempRef_tm_hour.argvalue).intValue();
		int minute = ((Integer) tempRef_tm_min.argvalue).intValue();
		int second = ((Integer) tempRef_tm_sec.argvalue).intValue();
		int ms = ((Integer) tempRef_tm_msec.argvalue).intValue();
		if (cycle == KeyFields.CYCLE_WEEK) {
			int dayOfWeek = DayOfWeek(year, month, day);
			if (dayOfWeek >= 5) {
				dayOfWeek = 4;
			}
			for (int i = 1; i <= dayOfWeek; i++) {
				double calcDate = CMathLib.耶稣(year, month, day - i, 0, 0, 0, 0);
				value = dataSource.Get(calcDate, field);
				if (!Double.isNaN(value)) {
					sumValue += value;
				}
			}
		} else if (cycle == KeyFields.CYCLE_MONTH) {
			for (int i = 1; i < day; i++) {
				double calcDate = CMathLib.耶稣(year, month, i, 0, 0, 0, 0);
				value = dataSource.Get(calcDate, field);
				if (!Double.isNaN(value)) {
					sumValue += value;
				}
			}
		} else if (cycle == 0) {
			int rowCount = dataSource.GetRowsCount();
			for (int i = 0; i < rowCount; i++) {
				value = dataSource.Get2(i, field);
				if (!Double.isNaN(value)) {
					sumValue += value;
				}
			}

		}
		return sumValue;
	}
}
