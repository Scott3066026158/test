package com.kunyi.bitamexJava.model;

public class RechargeInfoTable {
	/**
	 * 实名
	 */
	private String m_realName;
	/**
	 * 身份证号码
	 */
	private String m_idCardNo;
	/**
	 * 流水号
	 */
	private String m_serialNumber;
	/**
	 * 钱包地址
	 */
	private String m_walletAddress;
	/**
	 * 充值的钱包地址
	 */
	private String m_depositWalletAddress;
	/**
	 * 充值的币种
	 */
	private String m_coin;
	/**
	 * 充值金额
	 */
	private double m_depositVolume;
	/**
	 * 到账时间
	 */
	private long m_paymentDate;
	
	public String getM_realName() {
		return m_realName;
	}

	public void setM_realName(String m_realName) {
		this.m_realName = m_realName;
	}

	public String getM_idCardNo() {
		return m_idCardNo;
	}

	public void setM_idCardNo(String m_idCardNo) {
		this.m_idCardNo = m_idCardNo;
	}

	public String getM_serialNumber() {
		return m_serialNumber;
	}

	public void setM_serialNumber(String m_serialNumber) {
		this.m_serialNumber = m_serialNumber;
	}

	public String getM_walletAddress() {
		return m_walletAddress;
	}

	public void setM_walletAddress(String m_walletAddress) {
		this.m_walletAddress = m_walletAddress;
	}

	public String getM_depositWalletAddress() {
		return m_depositWalletAddress;
	}

	public void setM_depositWalletAddress(String m_depositWalletAddress) {
		this.m_depositWalletAddress = m_depositWalletAddress;
	}

	public String getM_coin() {
		return m_coin;
	}

	public void setM_coin(String m_coin) {
		this.m_coin = m_coin;
	}

	public double getM_depositVolume() {
		return m_depositVolume;
	}

	public void setM_depositVolume(double m_depositVolume) {
		this.m_depositVolume = m_depositVolume;
	}

	public long getM_paymentDate() {
		return m_paymentDate;
	}

	public void setM_paymentDate(long m_paymentDate) {
		this.m_paymentDate = m_paymentDate;
	}

	public String ConvertObjectToJson() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("{");
        //realName
        sBuilder.append("\"realName\":\"");
        sBuilder.append(m_realName + "\",");

        //idCardNo
        sBuilder.append("\"idCardNo\":\"");
        sBuilder.append(m_idCardNo + "\",");
        //serialNumber
        sBuilder.append("\"serialNumber\":\"");
        sBuilder.append(m_serialNumber + "\",");
        //walletAddress
        sBuilder.append("\"walletAddress\":\"");
        sBuilder.append(m_walletAddress + "\",");
        //depositWalletAddress
        sBuilder.append("\"depositWalletAddress\":\"");
        sBuilder.append(m_depositWalletAddress + "\",");
        //coin
        sBuilder.append("\"coin\":\"");
        sBuilder.append(m_coin + "\",");
        //depositVolume
        sBuilder.append("\"depositVolume\":\"");
        sBuilder.append(m_depositVolume + "\",");
        //paymentDate
        sBuilder.append("\"m_paymentDate\":\"");
        sBuilder.append(m_paymentDate + "\"");
        
        sBuilder.append("}");
        return sBuilder.toString();
    }
	
}
