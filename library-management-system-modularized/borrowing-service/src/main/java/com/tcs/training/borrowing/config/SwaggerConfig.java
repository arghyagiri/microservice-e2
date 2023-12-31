package com.tcs.training.borrowing.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI api() {
		return new OpenAPI().info(new Info().title("borrowing-service").description("Borrowing App").version("1.0.0"))
			.addServersItem(new Server().url("/"));
	}

}
