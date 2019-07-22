package com.gaia.autotrade.ws.bean;

public class ResponsePingMsg implements ResponseMsgInterfact {
	private long pong;

	public long getPong() {
		return pong;
	}

	public void setPong(long pong) {
		this.pong = pong;
	}

}
