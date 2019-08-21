package com.gaia.autotrade.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaia.autotrade.owsock.base.BaseService;
import com.gaia.autotrade.owsock.service.QuoteService;

@Component
public class SpringInit implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
	}

}