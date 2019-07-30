package com.gaia.autotrade.ws.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class TickPushTick {
	private long id;
	private long mrid;
	private double open;
	private double close;
	private double high;
	private double low;
	private double amount;
	private double vol;
	private double count;

	public long getId() {
		return id;
	}

	@JSONField(name = "id")
	public void setId(long id) {
		this.id = id;
	}

	public long getMrid() {
		return mrid;
	}

	@JSONField(name = "mrid")
	public void setMrid(long mrid) {
		this.mrid = mrid;
	}

	public double getOpen() {
		return open;
	}

	@JSONField(name = "open")
	public void setOpen(double open) {
		this.open = open;
	}

	public double getClose() {
		return close;
	}

	@JSONField(name = "close")
	public void setClose(double close) {
		this.close = close;
	}

	public double getHigh() {
		return high;
	}

	@JSONField(name = "high")
	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	@JSONField(name = "low")
	public void setLow(double low) {
		this.low = low;
	}

	public double getAmount() {
		return amount;
	}

	@JSONField(name = "amount")
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getVol() {
		return vol;
	}

	@JSONField(name = "vol")
	public void setVol(double vol) {
		this.vol = vol;
	}

	public double getCount() {
		return count;
	}

	@JSONField(name = "count")
	public void setCount(double count) {
		this.count = count;
	}

}
