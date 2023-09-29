package com.tcs.training.ms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI api() {
		return new OpenAPI()
			.info(new Info().title("library-management-system").description("Library Management App").version("1.0.0"))
			.addServersItem(new Server().url("/app"));
	}

}
