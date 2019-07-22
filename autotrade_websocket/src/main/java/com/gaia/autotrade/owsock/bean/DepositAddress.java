package com.gaia.autotrade.owsock.bean;

/**
 股票信息

 */
public class DepositAddress
{
    /**
     创建键盘精灵

     */
    public String m_address;
    public String m_coinCode;
    public String m_flag;
    public String m_userID;

    public DepositAddress()
    {

    }

    public void Copy(DepositAddress copy)
    {
        m_address = copy.m_address;
        m_coinCode = copy.m_coinCode;
        m_flag = copy.m_flag;
        m_userID = copy.m_userID;
    }
}