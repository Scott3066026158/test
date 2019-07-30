package com.gaia.autotrade.ws.bean;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class DepthPushTick {
	private long mrid;
	private long id;
	private long ts;
	private long version;
	private String ch;
	private List<ArrayList<Double>> bids;
	private List<ArrayList<Double>> asks;

	public long getMrid() {
		return mrid;
	}
	
	@JSONField(name = "mrid")
	public void setMrid(long mrid) {
		this.mrid = mrid;
	}

	public long getId() {
		return id;
	}

	@JSONField(name = "id")
	public void setId(long id) {
		this.id = id;
	}

	public long getTs() {
		return ts;
	}

	@JSONField(name = "ts")
	public void setTs(long ts) {
		this.ts = ts;
	}

	public long getVersion() {
		return version;
	}

	@JSONField(name = "version")
	public void setVersion(long version) {
		this.version = version;
	}

	public String getCh() {
		return ch;
	}

	@JSONField(name = "ch")
	public void setCh(String ch) {
		this.ch = ch;
	}

	public List<ArrayList<Double>> getBids() {
		return bids;
	}

	@JSONField(name = "bids")
	public void setBids(List<ArrayList<Double>> bids) {
		this.bids = bids;
	}

	public List<ArrayList<Double>> getAsks() {
		return asks;
	}

	@JSONField(name = "asks")
	public void setAsks(List<ArrayList<Double>> asks) {
		this.asks = asks;
	}

}
