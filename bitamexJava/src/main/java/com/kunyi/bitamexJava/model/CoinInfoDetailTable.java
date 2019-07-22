package com.kunyi.bitamexJava.model;

import com.kunyi.bitamexJava.util.StringUtil;

public class CoinInfoDetailTable {

	/**
	 * 币种简称
	 */
	private String m_simpleName;
	/**
	 * 币种全称
	 */
	private String m_name;
	/**
	 * 币种图标地址
	 */
	private String m_iconUrl;
	/**
	 * 基于的区块链
	 */
	private String m_blockChain;
	/**
	 * 合约地址
	 */
	private String m_contractAddress;
	/**
	 * 合约拥有者
	 */
	private String m_contractOwner;
	/**
	 * 小数位数
	 */
	private int m_digit;
	/**
	 * 官网
	 */
	private String m_officialWebsiteAddress;
	/**
	 * 源码地址
	 */
	private String m_sourceAddress;
	/**
	 * 区块浏览器地址
	 */
	private String m_blockBrowseAddress;
	/**
	 * 官方联系地址
	 */
	private String m_officialContactAddress;
	/**
	 * 发行量
	 */
	private double m_circulation;
	/**
	 * 当前市场流通量
	 */
	private double m_currentCirculation;
	/**
	 * 发型时间
	 */
	private long m_issueTime;
	/**
	 * 发送价
	 */
	private double m_issuePrice;
	/**
	 * 共识协议
	 */
	private String m_consensusAgreement;
	/**
	 * 加密算法
	 */
	private String m_encryptionAlgorithm;
	/**
	 * 白皮书地址
	 */
	private String m_whitePaperAddress;
	/**
	 * 团队地址
	 */
	private String m_teamInformation;
	
	/**
	 * 检测错误
	 * @return 通过检测返回true，发现错误返回false
	 */
	public boolean isError()
	{
		if (StringUtil.isEmpty(m_simpleName))
			return false;
		if (StringUtil.isEmpty(m_name))
			return false;
		if (StringUtil.isEmpty(m_iconUrl))
			return false;
		if (StringUtil.isEmpty(m_blockChain))
			return false;
		if (StringUtil.isEmpty(m_contractAddress))
			return false;
		if (StringUtil.isEmpty(m_contractOwner))
			return false;
		if (m_digit == 0)
			return false;
		if (StringUtil.isEmpty(m_officialWebsiteAddress))
			return false;
		if (StringUtil.isEmpty(m_sourceAddress))
			return false;
		if (StringUtil.isEmpty(m_blockBrowseAddress))
			return false;
		if (StringUtil.isEmpty(m_officialContactAddress))
			return false;
		if (m_circulation == 0.0)
			return false;
		if (m_currentCirculation == 0.0)
			return false;
		if (m_issueTime == 0)
			return false;
		if (m_issuePrice == 0.0)
			return false;
		if (StringUtil.isEmpty(m_consensusAgreement))
			return false;
		if (StringUtil.isEmpty(m_encryptionAlgorithm))
			return false;
		if (StringUtil.isEmpty(m_whitePaperAddress))
			return false;
		if (StringUtil.isEmpty(m_teamInformation))
			return false;
		return true;
	}
	
	/**
	 * 对象分化
	 */
	public CoinInfoTable createCoinInfoTale()
	{
		CoinInfoTable coinInfo = new CoinInfoTable();
		coinInfo.setM_createTime(m_issueTime);
		coinInfo.setM_digit(m_digit);
		coinInfo.setM_iconUrl(m_iconUrl);
		coinInfo.setM_name(m_name);
		coinInfo.setM_simpleName(m_simpleName);
		return coinInfo;
	}

	public String getM_simpleName() {
		return m_simpleName;
	}

	public String getM_name() {
		return m_name;
	}

	public String getM_iconUrl() {
		return m_iconUrl;
	}

	public String getM_blockChain() {
		return m_blockChain;
	}

	public String getM_contractAddress() {
		return m_contractAddress;
	}

	public String getM_contractOwner() {
		return m_contractOwner;
	}

	public int getM_digit() {
		return m_digit;
	}

	public String getM_officialWebsiteAddress() {
		return m_officialWebsiteAddress;
	}

	public String getM_sourceAddress() {
		return m_sourceAddress;
	}

	public String getM_blockBrowseAddress() {
		return m_blockBrowseAddress;
	}

	public String getM_officialContactAddress() {
		return m_officialContactAddress;
	}

	public double getM_circulation() {
		return m_circulation;
	}

	public double getM_currentCirculation() {
		return m_currentCirculation;
	}

	public long getM_issueTime() {
		return m_issueTime;
	}

	public double getM_issuePrice() {
		return m_issuePrice;
	}

	public String getM_consensusAgreement() {
		return m_consensusAgreement;
	}

	public String getM_encryptionAlgorithm() {
		return m_encryptionAlgorithm;
	}

	public String getM_whitePaperAddress() {
		return m_whitePaperAddress;
	}

	public String getM_teamInformation() {
		return m_teamInformation;
	}

	public void setM_simpleName(String m_simpleName) {
		this.m_simpleName = m_simpleName;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public void setM_iconUrl(String m_iconUrl) {
		this.m_iconUrl = m_iconUrl;
	}

	public void setM_blockChain(String m_blockChain) {
		this.m_blockChain = m_blockChain;
	}

	public void setM_contractAddress(String m_contractAddress) {
		this.m_contractAddress = m_contractAddress;
	}

	public void setM_contractOwner(String m_contractOwner) {
		this.m_contractOwner = m_contractOwner;
	}

	public void setM_digit(int m_digit) {
		this.m_digit = m_digit;
	}

	public void setM_officialWebsiteAddress(String m_officialWebsiteAddress) {
		this.m_officialWebsiteAddress = m_officialWebsiteAddress;
	}

	public void setM_sourceAddress(String m_sourceAddress) {
		this.m_sourceAddress = m_sourceAddress;
	}

	public void setM_blockBrowseAddress(String m_blockBrowseAddress) {
		this.m_blockBrowseAddress = m_blockBrowseAddress;
	}

	public void setM_officialContactAddress(String m_officialContactAddress) {
		this.m_officialContactAddress = m_officialContactAddress;
	}

	public void setM_circulation(double m_circulation) {
		this.m_circulation = m_circulation;
	}

	public void setM_currentCirculation(double m_currentCirculation) {
		this.m_currentCirculation = m_currentCirculation;
	}

	public void setM_issueTime(long m_issueTime) {
		this.m_issueTime = m_issueTime;
	}

	public void setM_issuePrice(double m_issuePrice) {
		this.m_issuePrice = m_issuePrice;
	}

	public void setM_consensusAgreement(String m_consensusAgreement) {
		this.m_consensusAgreement = m_consensusAgreement;
	}

	public void setM_encryptionAlgorithm(String m_encryptionAlgorithm) {
		this.m_encryptionAlgorithm = m_encryptionAlgorithm;
	}

	public void setM_whitePaperAddress(String m_whitePaperAddress) {
		this.m_whitePaperAddress = m_whitePaperAddress;
	}

	public void setM_teamInformation(String m_teamInformation) {
		this.m_teamInformation = m_teamInformation;
	}
	
	@Override
	public String toString() {
		return "CoinInfoDetailTable [m_simpleName=" + m_simpleName + ", m_name=" + m_name + ", m_iconUrl=" + m_iconUrl
				+ ", m_blockChain=" + m_blockChain + ", m_contractAddress=" + m_contractAddress + ", m_contractOwner="
				+ m_contractOwner + ", m_digit=" + m_digit + ", m_officialWebsiteAddress=" + m_officialWebsiteAddress
				+ ", m_sourceAddress=" + m_sourceAddress + ", m_blockBrowseAddress=" + m_blockBrowseAddress
				+ ", m_officialContactAddress=" + m_officialContactAddress + ", m_circulation=" + m_circulation
				+ ", m_currentCirculation=" + m_currentCirculation + ", m_issueTime=" + m_issueTime + ", m_issuePrice="
				+ m_issuePrice + ", m_consensusAgreement=" + m_consensusAgreement + ", m_encryptionAlgorithm="
				+ m_encryptionAlgorithm + ", m_whitePaperAddress=" + m_whitePaperAddress + ", m_teamInformation="
				+ m_teamInformation + "]";
	}
}
