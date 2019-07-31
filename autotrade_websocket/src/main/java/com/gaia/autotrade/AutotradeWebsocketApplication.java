package com.gaia.autotrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AutotradeWebsocketApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AutotradeWebsocketApplication.class, args);
		Applicaton.setContext(context);
	}

}
