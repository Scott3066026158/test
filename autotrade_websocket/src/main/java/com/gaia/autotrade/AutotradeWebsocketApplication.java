package com.gaia.autotrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.gaia.autotrade.owsock.global.DataCenter;

@SpringBootApplication
public class AutotradeWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutotradeWebsocketApplication.class, args);
		DataCenter.StartService();
		DataCenter.Connect();
	}

}
