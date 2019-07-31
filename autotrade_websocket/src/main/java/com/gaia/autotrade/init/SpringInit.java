package com.gaia.autotrade.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaia.autotrade.owsock.base.BaseService;
import com.gaia.autotrade.owsock.service.QuoteService;

@Component
public class SpringInit implements InitializingBean {

	//行情服务
	private QuoteService m_quoteService; 
	
    @Override
    public void afterPropertiesSet() throws Exception {
    	Connect();
    }
    
    @Autowired
    public void setQuoteService(QuoteService quoteService) {
    	m_quoteService = quoteService;
    }

	public void Connect()
	{
		BaseService.AddService(m_quoteService);
		int quoteSocket = m_quoteService.ConnectServer("ss.gaiafintech.com:13334");
		if(quoteSocket < 0)
		{
			System.out.println("连接失败!");
		}
		else
		{
			System.out.println("连接成功!");
		}
	}
    
}