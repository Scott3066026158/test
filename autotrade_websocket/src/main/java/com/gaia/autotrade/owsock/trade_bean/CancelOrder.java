package com.gaia.autotrade.owsock.trade_bean;

public class CancelOrder {
    public String m_flag;        //falg
    public String m_traderID;    //用户ID
    public int m_errorCode;        //0 失败 1成功//如果失败, 后面不要读取
    public String m_orderID;     //委托ID
    public String m_cancelID;	     //撤单ID
    public String m_tradePair;   //交易对子
    public double m_price;       //委托价格
    public double m_volume;      //委托手数
    public int m_dir;            //方向

    public void Copy(CancelOrder data)
    {
        m_flag = data.m_flag;
        m_traderID = data.m_traderID;
        m_errorCode = data.m_errorCode;
        m_orderID = data.m_orderID;
        m_cancelID = data.m_cancelID;
        m_tradePair = data.m_tradePair;
        m_price = data.m_price;
        m_volume = data.m_volume;
        m_dir = data.m_dir;
    }

    public boolean Equal(CancelOrder data)
    {
        if(data.m_orderID.equals(m_orderID))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}