package com.gaia.autotrade.owsock.bean;

import java.util.ArrayList;


public class MarketTradeTime {

    /// 集合竞价开始时间
    public int m_callAuctionTime ;
    /// 数据清空时间
    public int m_clearTime ;
    /// 延时分钟数
    public int m_delayTime ;
    /// 当日第一根K线的显示时间
    public int m_kLineTime;
    /// 本市场当前最后交易时间
    public int m_lastTradeTime ;
    /// 结束接受推送的时间
    public int m_pushTime;
    public ArrayList<TrendTimeUtility> m_trendTimeList = new ArrayList<TrendTimeUtility>();
}
