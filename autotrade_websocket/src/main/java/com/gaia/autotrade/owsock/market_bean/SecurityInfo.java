package com.gaia.autotrade.owsock.market_bean;

/**
 股票信息

 */
public class SecurityInfo
{
    /**
     创建键盘精灵

     */
    public SecurityInfo()
    {
    }

    //合约代码
    public String m_code;
    //交易所代码
    public String m_exchangeID;

    public String m_firstLeg;
  
    public String m_secondLeg;
    //是否正在交易
    public boolean m_isTrading;
    //合约名称
    public String m_name;
    //拼音
    public String m_pingyin;
    //最小变动价位
    public double m_priceTick;
    //最小变动手数
    public double m_amountTick;
    //状态
    public int m_status;
    //品种类型
    public int m_type;
    //保留小数位数
    public int m_digit;

    public SecurityInfo copy(SecurityInfo security)
    {
       this.m_code = new String(security.m_code);
       this.m_exchangeID = new String(security.m_exchangeID);
       this.m_firstLeg = new String(security.m_firstLeg);
       this.m_secondLeg = new String(security.m_secondLeg);
       this.m_name = new String(security.m_name);
       this.m_pingyin = new String(security.m_pingyin);
       this.m_isTrading = security.m_isTrading;
       this.m_priceTick = security.m_priceTick;
       this.m_amountTick = security.m_amountTick;
       this.m_type = security.m_type;
       this.m_status = security.m_status;
       this.m_digit = security.m_digit;
       return this;
    }
    
    public SecurityInfo copy() {
    	SecurityInfo security = new SecurityInfo();
    	security.m_code = new String(this.m_code);
    	security.m_exchangeID = new String(this.m_exchangeID);
    	security.m_firstLeg = new String(this.m_firstLeg);
    	security.m_secondLeg = new String(this.m_secondLeg);
    	security.m_name = new String(this.m_name);
    	security.m_pingyin = new String(this.m_pingyin);
    	security.m_isTrading = this.m_isTrading;
    	security.m_priceTick = this.m_priceTick;
    	security.m_amountTick = this.m_amountTick;
    	security.m_type = this.m_type;
    	security.m_status = this.m_status;
    	security.m_digit = this.m_digit;
    	return security;
    }
}