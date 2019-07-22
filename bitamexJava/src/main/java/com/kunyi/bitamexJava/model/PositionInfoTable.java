package com.kunyi.bitamexJava.model;

public class PositionInfoTable {
	
	/**
	 * 实名
	 */
	String m_realName;
	/**
	 * 手机号码
	 */
	String m_phone;
	/**
	 * 身份证号码
	 */
	String m_idCardNo;
	/**
	 * 钱包地址
	 */
	String m_walletAddress;
	/**
	 * 币种
	 */
	String m_coin;
	/**
	 * 可用数量
	 */
	double m_availableVolume;
	/**
	 * 冻结币种
	 */
	double m_frozenVolume;
	
	public String getM_realName() {
		return m_realName;
	}
	public void setM_realName(String m_realName) {
		this.m_realName = m_realName;
	}
	public String getM_phone() {
		return m_phone;
	}
	public void setM_phone(String m_phone) {
		this.m_phone = m_phone;
	}
	public String getM_idCardNo() {
		return m_idCardNo;
	}
	public void setM_idCardNo(String m_idCardNo) {
		this.m_idCardNo = m_idCardNo;
	}
	public String getM_walletAddress() {
		return m_walletAddress;
	}
	public void setM_walletAddress(String m_walletAddress) {
		this.m_walletAddress = m_walletAddress;
	}
	public String getM_coin() {
		return m_coin;
	}
	public void setM_coin(String m_coin) {
		this.m_coin = m_coin;
	}
	public double getM_availableVolume() {
		return m_availableVolume;
	}
	public void setM_availableVolume(double m_availableVolume) {
		this.m_availableVolume = m_availableVolume;
	}
	public double getM_frozenVolume() {
		return m_frozenVolume;
	}
	public void setM_frozenVolume(double m_frozenVolume) {
		this.m_frozenVolume = m_frozenVolume;
	}
	
	public String ConvertObjectToJson()
	{
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("{");
		//realName
		sBuilder.append("\"realName\":\"");
		sBuilder.append(m_realName + "\",");
		
		//phone
		sBuilder.append("\"phone\":\"");
		sBuilder.append(m_phone + "\",");
		
		//idCardNo
		sBuilder.append("\"idCardNo\":\"");
		sBuilder.append(m_idCardNo + "\",");
		
		//walletAddress
		sBuilder.append("\"walletAddress\":\"");
		sBuilder.append(m_walletAddress + "\",");
		
		//realName
		sBuilder.append("\"realName\":\"");
		sBuilder.append(m_realName + "\",");
		
		//coin
		sBuilder.append("\"coin\":\"");
		sBuilder.append(m_coin + "\",");
		
		//availableVolume
		sBuilder.append("\"availableVolume\":\"");
		sBuilder.append(m_availableVolume + "\",");
		
		   //frozenVolume
		sBuilder.append("\"frozenVolume\":\"");
		sBuilder.append(m_frozenVolume + "\"");
		
		sBuilder.append("}");
		return sBuilder.toString();
	}
	
	@Override
	public String toString() {
		return "PositionInfoTable [m_realName=" + m_realName + ", m_phone=" + m_phone + ", m_idCardNo=" + m_idCardNo
				+ ", m_walletAddress=" + m_walletAddress + ", m_coin=" + m_coin + ", m_availableVolume="
				+ m_availableVolume + ", m_frozenVolume=" + m_frozenVolume + "]";
	}

}
