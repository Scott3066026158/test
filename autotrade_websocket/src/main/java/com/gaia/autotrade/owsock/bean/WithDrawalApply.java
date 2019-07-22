package com.gaia.autotrade.owsock.bean;

public class WithDrawalApply {
    String m_flag;
    String m_userID;
    String m_coinCode;
    String m_revAddr;
    String m_reqID;//申请编号
    double m_reqTime;//申请时间
    double m_amount;

    public WithDrawalApply()
    {

    }

    public void Copy(WithDrawalApply copy)
    {
        m_flag = copy.m_flag;
        m_userID = copy.m_userID;
        m_coinCode = copy.m_coinCode;
        m_revAddr = copy.m_revAddr;
        m_reqID = copy.m_reqID;//申请编号
        m_reqTime =copy.m_reqTime;//申请时间
        m_amount = copy.m_amount;
    }
}
