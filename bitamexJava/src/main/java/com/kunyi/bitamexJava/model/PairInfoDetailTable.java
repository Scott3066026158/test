package com.kunyi.bitamexJava.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.kunyi.bitamexJava.util.StringUtil;

public class PairInfoDetailTable {
	/**
	 * 市场
	 */
	private String m_market;
	/**
	 * 币种
	 */
	private String m_coin;
	/**
	 * 生效时间
	 */
	private String m_takingEffectTime;
	/**
	 * 初始价格
	 */
	private double m_initPrice;
	/**
	 * 买入是否允许使用其他币种作为手续费
	 */
	private int m_isUseOtherCoinAsFeeForBuy;
	/**
	 * 卖出是否允许使用其他币种作为手续费
	 */
	private int m_isUseOtherCoinAsFeeForSell;
	/**
	 * 买入价格小数位数限制
	 */
	private int m_digitForBuyPrice;
	/**
	 * 卖出价格小数位数限制
	 */
	private int m_digitForSellPrice;
	/**
	 * 买入数量小数位数限制
	 */
	private int m_digitForBuyVolume;
	/**
	 * 卖出数量小数位数限制
	 */
	private int m_digitForSellVolume;
	/**
	 * 买入最低数量
	 */
	private double m_minVolumeForBuy;
	/**
	 * 卖出最低数量
	 */
	private double m_minVolumeForSell;
	/**
	 * Taker手续费百分比
	 */
	private double m_takerFeeRate;
	/**
	 * Taker手续费固定值
	 */
	private double m_takerFeeFixedVolume;
	/**
	 * 最小Taker手续费
	 */
	private double m_minTakerFee;
	/**
	 * Maker手续费百分比
	 */
	private double m_makerFeeRate;
	/**
	 * Maker手续费固定值
	 */
	private double m_makerFeeFixedVolume;
	/**
	 * 最小Maker手续费
	 */
	private double m_minMakerFee;

	public boolean isError() {
		if (StringUtil.isEmpty(m_market))
			return false;
		if (StringUtil.isEmpty(m_coin))
			return false;
		if (StringUtil.isEmpty(m_takingEffectTime))
			return false;
		if (m_initPrice == 0.0)
			return false;
		if (m_isUseOtherCoinAsFeeForBuy == 0)
			return false;
		if (m_isUseOtherCoinAsFeeForSell == 0)
			return false;
		if (m_minVolumeForBuy == 0.0)
			return false;
		if (m_minVolumeForSell == 0.0)
			return false;
		if (m_takerFeeRate == 0.0)
			return false;
		if (m_takerFeeFixedVolume == 0.0)
			return false;
		if (m_minTakerFee == 0.0)
			return false;
		if (m_makerFeeRate == 0.0)
			return false;
		if (m_makerFeeFixedVolume == 0.0)
			return false;
		if (m_minMakerFee == 0.0)
			return false;
		return true;
	}
	
	/**
	 * 对象分化
	 */
	public PairInfoTable createPairInfoTable(String username)
	{
		PairInfoTable pairInfo = new PairInfoTable();
		pairInfo.setM_initPrice(m_initPrice);
		pairInfo.setM_takingEffectTime(m_takingEffectTime);
		pairInfo.setM_tradePair(m_coin + "/" + m_market);
		pairInfo.setM_createTime(new Date().getTime() + 1000 * 60 * 60);
		pairInfo.setM_createPerson(username);
		return pairInfo;
	}

	public String getM_market() {
		return m_market;
	}

	public void setM_market(String m_market) {
		this.m_market = m_market;
	}

	public String getM_coin() {
		return m_coin;
	}

	public void setM_coin(String m_coin) {
		this.m_coin = m_coin;
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

	public int getM_isUseOtherCoinAsFeeForBuy() {
		return m_isUseOtherCoinAsFeeForBuy;
	}

	public void setM_isUseOtherCoinAsFeeForBuy(int m_isUseOtherCoinAsFeeForBuy) {
		this.m_isUseOtherCoinAsFeeForBuy = m_isUseOtherCoinAsFeeForBuy;
	}

	public int getM_isUseOtherCoinAsFeeForSell() {
		return m_isUseOtherCoinAsFeeForSell;
	}

	public void setM_isUseOtherCoinAsFeeForSell(int m_isUseOtherCoinAsFeeForSell) {
		this.m_isUseOtherCoinAsFeeForSell = m_isUseOtherCoinAsFeeForSell;
	}

	public int getM_digitForBuyPrice() {
		return m_digitForBuyPrice;
	}

	public void setM_digitForBuyPrice(int m_digitForBuyPrice) {
		this.m_digitForBuyPrice = m_digitForBuyPrice;
	}

	public int getM_digitForSellPrice() {
		return m_digitForSellPrice;
	}

	public void setM_digitForSellPrice(int m_digitForSellPrice) {
		this.m_digitForSellPrice = m_digitForSellPrice;
	}

	public int getM_digitForBuyVolume() {
		return m_digitForBuyVolume;
	}

	public void setM_digitForBuyVolume(int m_digitForBuyVolume) {
		this.m_digitForBuyVolume = m_digitForBuyVolume;
	}

	public int getM_digitForSellVolume() {
		return m_digitForSellVolume;
	}

	public void setM_digitForSellVolume(int m_digitForSellVolume) {
		this.m_digitForSellVolume = m_digitForSellVolume;
	}

	public double getM_minVolumeForBuy() {
		return m_minVolumeForBuy;
	}

	public void setM_minVolumeForBuy(double m_minVolumeForBuy) {
		this.m_minVolumeForBuy = m_minVolumeForBuy;
	}

	public double getM_minVolumeForSell() {
		return m_minVolumeForSell;
	}

	public void setM_minVolumeForSell(double m_minVolumeForSell) {
		this.m_minVolumeForSell = m_minVolumeForSell;
	}

	public double getM_takerFeeRate() {
		return m_takerFeeRate;
	}

	public void setM_takerFeeRate(double m_takerFeeRate) {
		this.m_takerFeeRate = m_takerFeeRate;
	}

	public double getM_takerFeeFixedVolume() {
		return m_takerFeeFixedVolume;
	}

	public void setM_takerFeeFixedVolume(double m_takerFeeFixedVolume) {
		this.m_takerFeeFixedVolume = m_takerFeeFixedVolume;
	}

	public double getM_minTakerFee() {
		return m_minTakerFee;
	}

	public void setM_minTakerFee(double m_minTakerFee) {
		this.m_minTakerFee = m_minTakerFee;
	}

	public double getM_makerFeeRate() {
		return m_makerFeeRate;
	}

	public void setM_makerFeeRate(double m_makerFeeRate) {
		this.m_makerFeeRate = m_makerFeeRate;
	}

	public double getM_makerFeeFixedVolume() {
		return m_makerFeeFixedVolume;
	}

	public void setM_makerFeeFixedVolume(double m_makerFeeFixedVolume) {
		this.m_makerFeeFixedVolume = m_makerFeeFixedVolume;
	}

	public double getM_minMakerFee() {
		return m_minMakerFee;
	}

	public void setM_minMakerFee(double m_minMakerFee) {
		this.m_minMakerFee = m_minMakerFee;
	}

	@Override
	public String toString() {
		return "PairInfoDetailTable [m_market=" + m_market + ", m_coin=" + m_coin + ", m_takingEffectTime="
				+ m_takingEffectTime + ", m_initPrice=" + m_initPrice + ", m_isUseOtherCoinAsFeeForBuy="
				+ m_isUseOtherCoinAsFeeForBuy + ", m_isUseOtherCoinAsFeeForSell=" + m_isUseOtherCoinAsFeeForSell
				+ ", m_digitForBuyPrice=" + m_digitForBuyPrice + ", m_digitForSellPrice=" + m_digitForSellPrice
				+ ", m_digitForBuyVolume=" + m_digitForBuyVolume + ", m_digitForSellVolume=" + m_digitForSellVolume
				+ ", m_minVolumeForBuy=" + m_minVolumeForBuy + ", m_minVolumeForSell=" + m_minVolumeForSell
				+ ", m_takerFeeRate=" + m_takerFeeRate + ", m_takerFeeFixedVolume=" + m_takerFeeFixedVolume
				+ ", m_minTakerFee=" + m_minTakerFee + ", m_makerFeeRate=" + m_makerFeeRate + ", m_makerFeeFixedVolume="
				+ m_makerFeeFixedVolume + ", m_minMakerFee=" + m_minMakerFee + "]";
	}

}
