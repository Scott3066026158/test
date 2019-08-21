package com.gaia.autotrade.http.entity;

public class SymbolInfo {

	private String baseCurrency;
	private String quotoCurrency;
	private Integer pricePrecision;
	private Integer amountPrecision;
	private String symbolPartition = "";
	private String symbol;
	private String state;
	private Integer valuePrecision = 0;
	private Long minOrderAmt = 0L;
	private Long maxOrderAmt = 0L;
	private Long minOrderValue = 0L;
	private int leverageRatio = 0;

	public SymbolInfo() {

	}

	public SymbolInfo(String baseCurrency, String quotoCurrency, Integer pricePrecision, Integer amountPrecision,
			String symbolPartition, String symbol, String state, Integer valuePrecision, Long minOrderAmt,
			Long maxOrderAmt, Long minOrderValue, int leverageRatio) {
		super();
		this.baseCurrency = baseCurrency;
		this.quotoCurrency = quotoCurrency;
		this.pricePrecision = pricePrecision;
		this.amountPrecision = amountPrecision;
		this.symbolPartition = symbolPartition;
		this.symbol = symbol;
		this.state = state;
		this.valuePrecision = valuePrecision;
		this.minOrderAmt = minOrderAmt;
		this.maxOrderAmt = maxOrderAmt;
		this.minOrderValue = minOrderValue;
		this.leverageRatio = leverageRatio;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getQuotoCurrency() {
		return quotoCurrency;
	}

	public void setQuotoCurrency(String quotoCurrency) {
		this.quotoCurrency = quotoCurrency;
	}

	public Integer getPricePrecision() {
		return pricePrecision;
	}

	public void setPricePrecision(Integer pricePrecision) {
		this.pricePrecision = pricePrecision;
	}

	public Integer getAmountPrecision() {
		return amountPrecision;
	}

	public void setAmountPrecision(Integer amountPrecision) {
		this.amountPrecision = amountPrecision;
	}

	public String getSymbolPartition() {
		return symbolPartition;
	}

	public void setSymbolPartition(String symbolPartition) {
		this.symbolPartition = symbolPartition;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getValuePrecision() {
		return valuePrecision;
	}

	public void setValuePrecision(Integer valuePrecision) {
		this.valuePrecision = valuePrecision;
	}

	public Long getMinOrderAmt() {
		return minOrderAmt;
	}

	public void setMinOrderAmt(Long minOrderAmt) {
		this.minOrderAmt = minOrderAmt;
	}

	public Long getMaxOrderAmt() {
		return maxOrderAmt;
	}

	public void setMaxOrderAmt(Long maxOrderAmt) {
		this.maxOrderAmt = maxOrderAmt;
	}

	public Long getMinOrderValue() {
		return minOrderValue;
	}

	public void setMinOrderValue(Long minOrderValue) {
		this.minOrderValue = minOrderValue;
	}

	public int getLeverageRatio() {
		return leverageRatio;
	}

	public void setLeverageRatio(int leverageRatio) {
		this.leverageRatio = leverageRatio;
	}

	public String convertStatusToState(int status) {
		if (status == 0) {
			this.state = "online";
		} else if (status == 1) {
			this.state = "suspend";
		} else {
			this.state = "offline";
		}
		return this.state;
	}
}
