package com.tcs.training.payment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Value("${spring.application.name}")
	String appName;

	@Bean
	public OpenAPI api() {
		return new OpenAPI().info(new Info().title(appName).description(appName).version("1.0.0"))
			.addServersItem(new Server().url("/"));
	}

}
