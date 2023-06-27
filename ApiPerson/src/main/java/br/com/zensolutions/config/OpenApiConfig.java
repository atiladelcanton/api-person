package br.com.zensolutions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenApi() {
		return new OpenAPI().info(new Info().title("Restfull Api with Java 18 and Spring Boot 3").version("v1")
				.description("Api Person studing").license(new License().name("MIT")));
	}
}
