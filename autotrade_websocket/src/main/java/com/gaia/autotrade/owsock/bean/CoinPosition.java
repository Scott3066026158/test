package com.gaia.autotrade.owsock.bean;

public class CoinPosition
{
    public CoinPosition()
    {
    }

    //经纪公司代码
    public String m_brokerID = "";
    //合约代码
    public String m_code = "";
    //交易所代码
    public String m_exchangeID = "";
    public double m_frozenVolume;
    //投资者代码
    public String m_investorID = "";
    //今日持仓
    public double m_volume;

    /**
     复制数据

     @param postion 数据
     */
    public void Copy(CoinPosition postion)
    {
        m_brokerID = postion.m_brokerID;
        m_code = postion.m_code;
        m_exchangeID = postion.m_exchangeID;
        m_frozenVolume = postion.m_frozenVolume;
        m_investorID = postion.m_investorID;
        m_volume = postion.m_volume;
    }

    /**
     比较是否相同

     @param position 数据
     @return 是否相同
     */
    public final boolean Equal(CoinPosition position)
    {
        if (position == null)
        {
            return false;
        }
        if (m_brokerID.equals(position.m_brokerID)
            && m_code.equals(position.m_code)
            && m_exchangeID.equals(position.m_exchangeID)
                && m_frozenVolume == position.m_frozenVolume
            && m_investorID.equals(position.m_investorID)
            && m_volume == position.m_volume)
        {
            return true;
        }
        return false;
    }
}
