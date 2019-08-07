package com.gaia.autotrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AutotradeWebsocketApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AutotradeWebsocketApplication.class, args);
		Applicaton.setContext(context);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
