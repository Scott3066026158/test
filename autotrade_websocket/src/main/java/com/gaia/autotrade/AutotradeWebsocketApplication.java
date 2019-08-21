package com.gaia.autotrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.gaia.autotrade.init.AfterAppInit;

@SpringBootApplication
public class AutotradeWebsocketApplication extends SpringBootServletInitializer {

	private static AfterAppInit m_initApp;

	@Autowired
	private void setAfterAppInit(AfterAppInit afterAppInit) {
		m_initApp = afterAppInit;
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AutotradeWebsocketApplication.class, args);
		Applicaton.setContext(context);
		m_initApp.Init();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}
}
