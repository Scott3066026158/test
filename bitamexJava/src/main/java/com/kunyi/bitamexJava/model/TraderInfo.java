package com.kunyi.bitamexJava.model;

public class TraderInfo {
	/**
	 * 交易对子
	 */
	private String code;
	
	/**
	 * 成交ID
	 */
	private String transId;
	/**
	 * 订单ID
	 */
	private String orderId;
	/**
	 * 交易者ID
	 */
	private String traderId;
	/**
	 * 交易数量
	 */
	private Double volume;
	/**
	 * 成交价格
	 */
	private Double price;
	/**
	 * 成交时间戳
	 */
	private Long timestamp;
	/**
	 * 交易方向，买或卖
	 */
	private Integer direction;
	/**
	 * 是否完成
	 */
	private Integer isFinish;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Integer getDirection() {
		return direction;
	}
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public Integer getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}
	
	public String getTraderId() {
		return traderId;
	}
	public void setTraderId(String traderId) {
		this.traderId = traderId;
	}
	@Override
	public String toString() {
		return "TraderInfo [code=" + code + ", transId=" + transId + ", orderId=" + orderId + ", volume=" + volume
				+ ", price=" + price + ", timestamp=" + timestamp + ", direction=" + direction + ", isFinish="
				+ isFinish + "]";
	}
	
}
