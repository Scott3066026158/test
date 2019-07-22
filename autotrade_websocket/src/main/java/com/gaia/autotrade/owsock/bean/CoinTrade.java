package com.gaia.autotrade.owsock.bean;

public class CoinTrade {

    public CoinTrade()
    {
    }
    //是否修正
    public boolean m_bModify = false;
    //经纪公司代码
    public String m_brokerID = "";
    //合约代码
    public String m_code = "";
    //手续费
    public double m_commission;
    //买卖方向
    public String m_direction = "";
    //市场代码
    public String m_exchangeID = "";
    //交易日
    public String m_excutionDate = "";
    //投资者代码
    public String m_investorID = "";
    public double m_openPrice;
    public String m_openDate = "";
    public String m_openTime = "";
    //报单引用
    public String m_orderRef = "";
    //报单编号
    public String m_orderSysID = "";
    //价格
    public double m_price;
    //成交编号
    public String m_tradeID = "";
    //成交时期
    public String m_tradeDate = "";
    //成交时间
    public String m_tradeTime = "";
    //交易日
    public String m_tradingDay = "";
    //成交类型
    public String m_tradeType = "";
    //数量
    public double m_volume;
    /**
     复制数据

     @param data 数据
     */
    public final void Copy(CoinTrade data)
    {
        if (data == null)
        {
            return;
        }
        m_bModify = data.m_bModify;
        m_brokerID = data.m_brokerID;
        m_code = data.m_code;
        m_commission = data.m_commission;
        m_direction = data.m_direction;
        m_exchangeID = data.m_exchangeID;
        m_investorID = data.m_investorID;
        m_orderRef = data.m_orderRef;
        m_orderSysID = data.m_orderSysID;
        m_openPrice = data.m_openPrice;
        m_openDate = data.m_openDate;
        m_openTime = data.m_openTime;
        m_price = data.m_price;
        m_tradeID = data.m_tradeID;
        m_tradeDate = data.m_tradeDate;
        m_tradeTime = data.m_tradeTime;
        m_tradingDay = data.m_tradingDay;
        m_tradeType = data.m_tradeType;
        m_volume = data.m_volume;
    }

    /**
     比较是否相同

     @param data 数据
     @return 是否相同
     */
    public final boolean Equal(CoinTrade data)
    {
        if (data == null)
        {
            return false;
        }
        if (m_brokerID.equals(data.m_brokerID)
            && m_bModify == data.m_bModify
            && m_code.equals(data.m_code)
            && m_commission == data.m_commission
            && m_direction.equals(data.m_direction)
            && m_exchangeID.equals(data.m_exchangeID)
            && m_investorID.equals(data.m_investorID)
            && m_orderRef.equals(data.m_orderRef)
            && m_orderSysID.equals(data.m_orderSysID)
                && m_openPrice == data.m_openPrice
                && m_openDate.equals(data.m_openDate)
                && m_openTime.equals(data.m_openTime)
            && m_price == data.m_price
            && m_tradeID.equals(data.m_tradeID)
            && m_tradeDate.equals(data.m_tradeDate)
            && m_tradeTime.equals(data.m_tradeTime)
            && m_tradingDay.equals(data.m_tradingDay)
            && m_tradeType.equals(data.m_tradeType)
            && m_volume == data.m_volume)
        {
            return true;
        }
        return false;
    }
}
