package com.gaia.autotrade.owsock.bean;

/**
 公共字段

 */
public class KeyFields
{
    /**
     收盘价

     */
    public static final String CLOSE = "CLOSE";
    /**
     最高价

     */
    public static final String HIGH = "HIGH";
    /**
     最低价

     */
    public static final String LOW = "LOW";
    /**
     开盘价
     */
    public static final String OPEN = "OPEN";
    /**
     成交量
     */
    public static final String VOL = "VOL";
    /**
     成交额
     */
    public static final String AMOUNT = "AMOUNT";

    /**
     平均价格
     */
    public static final String AVGPRICE = "AVGPRICE";

    /**
     结算价
     */
    public static final String SETTLEMENTPRICE = "SETTLEMENTPRICE";

    /**
     收盘价字段
     */
    public static final int CLOSE_INDEX = 0;
    /**
     最高价字段
     */
    public static final int HIGH_INDEX = 1;
    /**
     最低价字段
     */
    public static final int LOW_INDEX = 2;
    /**
     开盘价字段
     */
    public static final int OPEN_INDEX = 3;
    /**
     成交量字段
     */
    public static final int VOL_INDEX = 4;
    /**
     * 成交额字段
     */
    public static final int AMOUNT_INDEX = 5;

    /**
     平均价格字段
     */
    public static final int AVGPRICE_INDEX = 6;

    /**
     * 结算价
     */
    public static final int SETTLEMENTPRICE_INDEX = 7;

    public static final int VOLHISTORY_INDEX = 0;
    public static final int AMOUNTHISTORY_INDEX = 1;

    //1分钟
    public static final int CYCLE_MINUTE_1 = 1;
    //3分钟
    public static final int CYCLE_MINUTE_3 = 3;
    //5分钟
    public static final int CYCLE_MINUTE_5 = 5;
    //10分钟
    public static final int CYCLE_MINUTE_10 = 10;
    //15分钟
    public static final int CYCLE_MINUTE_15 = 15;
    //30分钟
    public static final int CYCLE_MINUTE_30 = 30;
    //60分钟
    public static final int CYCLE_MINUTE_60 = 60;
    //日线
    public static final int CYCLE_DAY = 1440;
    //周线
    public static final int CYCLE_WEEK = 10080;
    //月线
    public static final int CYCLE_MONTH = 43200;
}