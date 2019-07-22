package com.kunyi.bitamexJava.model;

public class WithdrawInfoTable {
	/**
	 * 实名
	 */
	private String m_realName;
	/**
	 * 手机号码
	 */
	private String m_phone;
	/**
	 * 身份证号码
	 */
	private String m_idCardNo;
	/**
	 * 交易流水号
	 */
	private String m_serialNumber;
	/**
	 * 钱包地址
	 */
	private String m_walletAddress;
	/**
	 * 收款地址
	 */
	private String m_receiptAddress;
	/**
	 * 出金币种
	 */
	private String m_coin;
	/**
	 * 出金数量
	 */
	private double m_withdrawVolume;
	/**
	 * 到账时间
	 */
	private long m_paymentDate;
	
	public String ConvertObjectToJson() {
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
        //serialNumber
        sBuilder.append("\"serialNumber\":\"");
        sBuilder.append(m_serialNumber + "\",");
        //walletAddress
        sBuilder.append("\"walletAddress\":\"");
        sBuilder.append(m_walletAddress + "\",");
        //receiptAddress
        sBuilder.append("\"receiptAddress\":\"");
        sBuilder.append(m_receiptAddress + "\",");
        //coin
        sBuilder.append("\"coin\":\"");
        sBuilder.append(m_coin + "\",");
        //withdrawVolume
        sBuilder.append("\"withdrawVolume\":\"");
        sBuilder.append(m_withdrawVolume + "\",");
        //paymentDate
        sBuilder.append("\"paymentDate\":\"");
        sBuilder.append(m_paymentDate + "\"");
        sBuilder.append("}");
        return sBuilder.toString();
    }

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

	public String getM_receiptAddress() {
		return m_receiptAddress;
	}

	public void setM_receiptAddress(String m_receiptAddress) {
		this.m_receiptAddress = m_receiptAddress;
	}

	public String getM_coin() {
		return m_coin;
	}

	public void setM_coin(String m_coin) {
		this.m_coin = m_coin;
	}

	public double getM_withdrawVolume() {
		return m_withdrawVolume;
	}

	public void setM_withdrawVolume(double m_withdrawVolume) {
		this.m_withdrawVolume = m_withdrawVolume;
	}

	public long getM_paymentDate() {
		return m_paymentDate;
	}

	public void setM_paymentDate(long m_paymentDate) {
		this.m_paymentDate = m_paymentDate;
	}
	
}
