package com.gaia.autotrade.ws.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class TickDetailPushTick {
	// unix时间，同时作为消息ID
	private Integer id;
	// unix系统时间
	private Integer ts;
	// 24小时成交量
	private Double amount;
	// 24小时成交笔数
	private Integer count;
	// 24小时开盘价
	private Double open;
	// 最新价
	private Double close;
	// 24小时最低价
	private Double low;
	// 24小时最高价
	private Double high;
	// 24小时成交额
	private Double vol;

	public Integer getId() {
		return id;
	}

	@JSONField(name = "id")
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTs() {
		return ts;
	}

	@JSONField(name = "ts")
	public void setTs(Integer ts) {
		this.ts = ts;
	}

	public Double getAmount() {
		return amount;
	}

	@JSONField(name = "amount")
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getCount() {
		return count;
	}

	@JSONField(name = "count")
	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getOpen() {
		return open;
	}

	@JSONField(name = "open")
	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getClose() {
		return close;
	}

	@JSONField(name = "close")
	public void setClose(Double close) {
		this.close = close;
	}

	public Double getLow() {
		return low;
	}

	@JSONField(name = "low")
	public void setLow(Double low) {
		this.low = low;
	}

	public Double getHigh() {
		return high;
	}

	@JSONField(name = "high")
	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getVol() {
		return vol;
	}

	@JSONField(name = "vol")
	public void setVol(Double vol) {
		this.vol = vol;
	}
}
