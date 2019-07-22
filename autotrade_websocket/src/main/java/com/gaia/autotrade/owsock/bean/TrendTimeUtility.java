package com.gaia.autotrade.owsock.bean;

public class TrendTimeUtility {
    public int m_nCloseTime;
    public int m_nOpenTime;

    public TrendTimeUtility()
    {
        m_nCloseTime = 0;
        m_nOpenTime  = 0;
    }

    public TrendTimeUtility(int open, int close)
    {
        m_nOpenTime = open;
        m_nCloseTime = close;
    }
}
