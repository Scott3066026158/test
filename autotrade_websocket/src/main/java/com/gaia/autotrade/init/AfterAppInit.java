package com.gaia.autotrade.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaia.autotrade.owsock.base.BaseService;
import com.gaia.autotrade.owsock.service.QuoteService;

@Component
public class AfterAppInit {

	// 行情服务
	private QuoteService m_quoteService;

	@Autowired
	public void setQuoteService(QuoteService quoteService) {
		m_quoteService = quoteService;
	}

	public void Init() {
		Connect();
	}

	public void Connect() {
		String url = "allinex.io:5002";
		BaseService.AddService(m_quoteService);
		int quoteSocket = m_quoteService.ConnectServer(url);
		if (quoteSocket < 0) {
			System.out.println("连接失败，url:" + url);
		} else {
			System.out.println("连接成功，url:" + url);
		}
	}

}
