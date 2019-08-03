package com.gaia.autotrade.ws.bean;

public class TradePushTick {
	
	// 唯一成交ID
	private Integer id;
	// 成交量
	private double amount;
	// 成交价
	private double price;
	// 成交时间 (UNIX epoch time in millisecond)
	private Integer ts;
	// 成交主动方 (taker的订单方向) : 'buy' or 'sell'
	private String direction;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getTs() {
		return ts;
	}

	public void setTs(Integer ts) {
		this.ts = ts;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
