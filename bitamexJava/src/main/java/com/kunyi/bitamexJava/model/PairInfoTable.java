package com.kunyi.bitamexJava.model;

public class PairInfoTable {
	/**
	 * 交易对子
	 */
	private String m_tradePair;
	/**
	 * 生效时间
	 */
	private String m_takingEffectTime;
	/**
	 * 初始价格
	 */
	private double m_initPrice;
	/**
	 * 手续费
	 */
	private double m_fee;
	/**
	 * 创建人
	 */
	private String m_createPerson;
	/**
	 * 创建时间
	 */
	private long m_createTime;
	/**
	 * 对子开关
	 */
	private int m_status;
	
	public String ConvertObjectToJson() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("{");
        //tradePair
        sBuilder.append("\"tradePair\":\"");
        sBuilder.append(m_tradePair + "\",");
        
        //takingEffectTime
        sBuilder.append("\"takingEffectTime\":\"");
        sBuilder.append(m_takingEffectTime + "\",");
        
        //initPrice
        sBuilder.append("\"initPrice\":\"");
        sBuilder.append(m_initPrice + "\",");
        
        //fee
        sBuilder.append("\"fee\":\"");
        sBuilder.append(m_fee + "\",");
        
        //createPerson
        sBuilder.append("\"createPerson\":\"");
        sBuilder.append(m_createPerson + "\",");
        
        //createTime
        sBuilder.append("\"createTime\":\"");
        sBuilder.append(m_createTime + "\",");

        //status
        sBuilder.append("\"status\":\"");
        sBuilder.append(m_status + "\"");
        
        sBuilder.append("}");
        return sBuilder.toString();
    }

	public String getM_tradePair() {
		return m_tradePair;
	}

	public void setM_tradePair(String m_tradePair) {
		this.m_tradePair = m_tradePair;
	}

	public String getM_takingEffectTime() {
		return m_takingEffectTime;
	}

	public void setM_takingEffectTime(String m_takingEffectTime) {
		this.m_takingEffectTime = m_takingEffectTime;
	}

	public double getM_initPrice() {
		return m_initPrice;
	}

	public void setM_initPrice(double m_initPrice) {
		this.m_initPrice = m_initPrice;
	}

	public double getM_fee() {
		return m_fee;
	}

	public void setM_fee(double m_fee) {
		this.m_fee = m_fee;
	}

	public String getM_createPerson() {
		return m_createPerson;
	}

	public void setM_createPerson(String m_createPerson) {
		this.m_createPerson = m_createPerson;
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
