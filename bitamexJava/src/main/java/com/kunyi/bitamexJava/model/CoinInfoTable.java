package com.kunyi.bitamexJava.model;

public class CoinInfoTable {
	/**
	 * 币种图标地址
	 */
	private String m_iconUrl;
	/**
	 * 币种中文名
	 */
	private String m_name;
	/**
	 * 币种简称
	 */
	private String m_simpleName;
	/**
	 * 币种小数位
	 */
	private int m_digit;
	/**
	 * 出金手续费率
	 */
	private double m_withdrawFeeRate;
	/**
	 * 每日体现最大值
	 */
	private double m_withdrawLimitbyDay;
	/**
	 * 允许最小的出金金额
	 */
	private double m_minWithdraw;
	/**
	 * 币种状态(是否可用)
	 */
	private int m_status;
	/**
	 * 币种创建时间
	 */
	private long m_createTime;
	
	public String ConvertObjectToJson() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("{");
        //iconUrl
        sBuilder.append("\"iconUrl\":\"");
        sBuilder.append(m_iconUrl + "\",");
        //chineseName
        sBuilder.append("\"name\":\"");
        sBuilder.append(m_name + "\",");
        
        //simpleName
        sBuilder.append("\"simpleName\":\"");
        sBuilder.append(m_simpleName + "\",");
        
        //digit
        sBuilder.append("\"digit\":\"");
        sBuilder.append(m_digit + "\",");
        
        //withdrawFeeRate
        sBuilder.append("\"withdrawFeeRate\":\"");
        sBuilder.append(m_withdrawFeeRate + "\",");
        
        //withdrawLimitbyDay
        sBuilder.append("\"withdrawLimitbyDay\":\"");
        sBuilder.append(m_withdrawLimitbyDay + "\",");
        
        //minWithdraw
        sBuilder.append("\"minWithdraw\":\"");
        sBuilder.append(m_minWithdraw + "\",");
        
        //status
        sBuilder.append("\"status\":\"");
        sBuilder.append(m_status + "\",");
        
        //createTime
        sBuilder.append("\"createTime\":\"");
        sBuilder.append(m_createTime + "\"");

        sBuilder.append("}");
        return sBuilder.toString();
    }

	public String getM_iconUrl() {
		return m_iconUrl;
	}

	public void setM_iconUrl(String m_iconUrl) {
		this.m_iconUrl = m_iconUrl;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_simpleName() {
		return m_simpleName;
	}

	public void setM_simpleName(String m_simpleName) {
		this.m_simpleName = m_simpleName;
	}

	public int getM_digit() {
		return m_digit;
	}

	public void setM_digit(int m_digit) {
		this.m_digit = m_digit;
	}

	public double getM_withdrawFeeRate() {
		return m_withdrawFeeRate;
	}

	public void setM_withdrawFeeRate(double m_withdrawFeeRate) {
		this.m_withdrawFeeRate = m_withdrawFeeRate;
	}

	public double getM_withdrawLimitbyDay() {
		return m_withdrawLimitbyDay;
	}

	public void setM_withdrawLimitbyDay(double m_withdrawLimitbyDay) {
		this.m_withdrawLimitbyDay = m_withdrawLimitbyDay;
	}

	public double getM_minWithdraw() {
		return m_minWithdraw;
	}

	public void setM_minWithdraw(double m_minWithdraw) {
		this.m_minWithdraw = m_minWithdraw;
	}

	public int getM_status() {
		return m_status;
	}

	public void setM_status(int m_status) {
		this.m_status = m_status;
	}

	public long getM_createTime() {
		return m_createTime;
	}

	public void setM_createTime(long m_createTime) {
		this.m_createTime = m_createTime;
	}
}
