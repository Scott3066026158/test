package com.gaia.autotrade.owsock.trade_bean;

public class HistoryRechargeRecord {
    //用户ID
    public String m_userID;
    //充值币种
    public String m_coin;
    //充值总数
    public double m_amount;
    //时间戳
    public double m_timestamp;
    //充值方式
    public int m_way;

    //复制数据
    public final void Copy(HistoryRechargeRecord data)
    {
        if (data == null)
        {
            return;
        }
        this.m_userID = data.m_userID;
        this.m_coin = data.m_coin;
        this.m_amount = data.m_amount;
        this.m_timestamp = data.m_timestamp;
        this.m_way = data.m_way;
    }

}
